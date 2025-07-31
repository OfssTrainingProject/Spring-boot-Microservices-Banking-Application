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
	
	
	private String generateFormattedCardNumber() {
	    StringBuilder sb = new StringBuilder("4"); // Start with a prefix (4)

	    for (int i = 0; i < 15; i++) {
	        sb.append((int) (Math.random() * 10)); // Add 15 random digits
	    }

	    // Format as XXXX-XXXX-XXXX-XXXX
	    String raw = sb.toString();
	    return raw.replaceAll("(.{4})(?=.)", "$1-");  // Insert dash after every 4 digits
	}

	
	
	@Override
	public List<CreditCard> getAllCards() {
	    return repository.findAll();
	}


    @Override
    public CreditCard issueCard(CreditCardDto dto) {
        CreditCard card = new CreditCard();
        card.setCardNumber(generateFormattedCardNumber());
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
    public CreditCard getCardByCardId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    @Override
    public List<CreditCard> getCardsByAccount(String accountNumber) {
        List<CreditCard> cards = repository.findByAccountNumber(accountNumber);
        if (cards == null || cards.isEmpty()) {
            throw new ResourceNotFoundException("No cards found for account number: " + accountNumber);
        }
        return cards;
    }


    @Override
    public CreditCard updateCard(Long id, CreditCardDto dto) {
        CreditCard card = getCardByCardId(id);
        card.setCardType(dto.getCardType());
        //card.setCustomerName(dto.getCustomerName());
        card.setCreditLimit(dto.getCreditLimit());
        card.setExpiryDate(dto.getExpiryDate());
        return repository.save(card);
    }

    @Override
    public void deleteCard(Long id) {
        CreditCard card = getCardByCardId(id);
        repository.delete(card);
    }
	
}
