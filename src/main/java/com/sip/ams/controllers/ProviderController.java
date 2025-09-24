package com.sip.ams.controllers;

import java.util.List;
import java.util.Optional;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ProviderRepository;
import com.sip.ams.services.ProviderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("providers")
public class ProviderController {
	@Autowired
	ProviderService providerService;
	

	@Operation(summary = "Récupération de tous les providers", description = "Retourne la liste complète des providers enregistrés dans la base de données.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Liste des providers récupérée avec succès"),
			@ApiResponse(responseCode = "500", description = "Erreur lors de la récupération des providers") })
	@GetMapping("/")
	public ResponseEntity<List<Provider>> getAllProviders() {
		return new ResponseEntity<>((List<Provider>) this.providerService.getAllProviders(), HttpStatus.OK);
	}

	@Operation(summary = "Ajout un noveau provider", description = "ajouter un nv provider dans la base de donnees.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Insertion avec succees"),
			@ApiResponse(responseCode = "500", description = "probleme lors de l'insertin de provider") })
	@PostMapping("/")
	public ResponseEntity<Provider> saveProvider(@RequestBody Provider p) {

		return new ResponseEntity<>(this.providerService.saveProvider(p), HttpStatus.CREATED);
	}

	@Operation(summary = "Recuperer un Provider par id", description = "recuperer un provider par id dans la base de donnees.")
	@ApiResponses(value = { @ApiResponse(responseCode = "304", description = "recuperation  avec succees"),
			@ApiResponse(responseCode = "404", description = "provider inexistant") })
	
	@GetMapping("/{id}")
	public ResponseEntity<Provider> getProviderById(@PathVariable int id) {
		Optional <Provider> opt = this.providerService.getProviderById(id);
		if (opt.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(opt.get(), HttpStatus.FOUND); //CODE 304
	}
	@Operation(summary = "Supression un provider par id", description = "supprimer un provider par id dans la base de donnees.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "suppression provider  avec succees"),
			@ApiResponse(responseCode = "404", description = "suppression echoue") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Provider> DeleteProviderById(@PathVariable int id) {
		Optional <Provider> opt = this.providerService.getProviderById(id);
		if (opt.isEmpty())
			return ResponseEntity.notFound().build(); // CODE 404
		else {
			this.providerService.DeleteProviderById(id);
			return ResponseEntity.noContent().build(); //CODE 204
			
		}
		
			
	}
	@Operation(summary = "Mise a jourd'un provider ", description = "Mise a jour d'un provider par id dans la base de donnees.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Mise a jour avec succees"),
			@ApiResponse(responseCode = "404", description = "Provider inexistant") })
	@PutMapping("/")
	public ResponseEntity<Provider> updateProvider(@RequestBody Provider provider){
		Optional <Provider> opt = this.providerService.getProviderById(provider.getId());
		if (opt.isEmpty())
			return ResponseEntity.notFound().build(); // CODE 404
		else {
			Provider savedProvider = opt.get();
			savedProvider.setName(provider.getName());
			savedProvider.setEamil(provider.getEmail());
			savedProvider.setAddress(provider.getAddress());
			return new ResponseEntity<>(this.providerService.saveProvider(savedProvider), HttpStatus.OK);

		}
		
	}
}
