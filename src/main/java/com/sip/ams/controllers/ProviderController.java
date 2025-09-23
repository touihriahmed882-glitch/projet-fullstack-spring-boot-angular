package com.sip.ams.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ProviderRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("providers")
public class ProviderController {
	@Autowired 
	ProviderRepository providerRepository ;
	 @Operation(summary = "Récupération de tous les providers",
             description = "Retourne la liste complète des providers enregistrés dans la base de données.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Liste des providers récupérée avec succès"),
          @ApiResponse(responseCode = "400", description = "Erreur lors de la récupération des providers")
  })
	@GetMapping("/")
	public List<Provider>getAllProviders()
	{
		return(List<Provider>) providerRepository.findAll();
	}
	@PostMapping("/")
	public Provider saveProvider(@RequestBody Provider p) {
		
		return providerRepository.save(p);
	}

}
