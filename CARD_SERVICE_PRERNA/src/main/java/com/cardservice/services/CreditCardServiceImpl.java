package com.cardservice.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardservice.dto.CreditCardDto;
import com.cardservice.exceptions.ResourceNotFoundException;
import com.cardservice.models.CreditCard;
import com.cardservice.repositories.CreditCardRepository;

@Service
public class CreditCardServiceImpl implements CreditCardService{
	
	@Autowired
    private CreditCardRepository repository;
	
	@Override
	public List<CreditCard> getAllCards() {
	    return repository.findAll();
	}


    @Override
    public CreditCard issueCard(CreditCardDto dto) {
        CreditCard card = new CreditCard();
        card.setCardNumber(UUID.randomUUID().toString());
        card.setCardType(dto.getCardType());
        card.setAccountNumber(dto.getAccountNumber());
        card.setCustomerName(dto.getCustomerName());
        card.setCreditLimit(dto.getCreditLimit());
        card.setCurrentUsage(0);
        card.setStatus("ACTIVE");
        card.setIssueDate(LocalDate.now());
        card.setExpiryDate(dto.getExpiryDate());
        return repository.save(card);
    }
    
    @Override
    public CreditCard getCard(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    @Override
    public List<CreditCard> getCardsByAccount(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

    @Override
    public CreditCard updateCard(Long id, CreditCardDto dto) {
        CreditCard card = getCard(id);
        card.setCardType(dto.getCardType());
        card.setCustomerName(dto.getCustomerName());
        card.setCreditLimit(dto.getCreditLimit());
        card.setExpiryDate(dto.getExpiryDate());
        return repository.save(card);
    }

    @Override
    public void deleteCard(Long id) {
        CreditCard card = getCard(id);
        repository.delete(card);
    }
	
}
