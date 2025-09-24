package com.sip.ams.services;
import java.util.List;
import java.util.Optional;

import com.sip.ams.entities.Provider;

public interface ProviderService {
	public List<Provider> getAllProviders();
	public Optional<Provider> getProviderById (int id);
	public void DeleteProviderById(int id);
	public Provider saveProvider (Provider provider);
}
