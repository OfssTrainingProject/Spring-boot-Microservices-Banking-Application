package com.bank.transactionservice.service;

import com.bank.common.dto.TransactionRequest;
import com.bank.common.dto.TransactionResponse;
import com.bank.transactionservice.config.AccountClient;
import com.bank.transactionservice.exception.TransactionNotFoundException;
import com.bank.transactionservice.mapper.TransactionMapper;
import com.bank.transactionservice.model.Transaction;
import com.bank.transactionservice.model.Transaction.TransactionStatus;
import com.bank.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
	@Autowired
	private final AccountClient accountClient;
	@Autowired
	private final TransactionRepository transactionRepository;

	public Transaction getTransactionById(Long id) {
		return transactionRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found"));
	}

	public Transaction getTransactionByUserId(Long id) {
		return transactionRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + id + " not found"));
	}

	@Transactional
	public TransactionResponse transferAmount(TransactionRequest request) {
		StringBuilder sb = new StringBuilder();
		Transaction transaction = new Transaction();
		transaction.setFromAccountUserId(request.getFromUserId());
		transaction.setFromAccount(request.getFromAccount());
		transaction.setFromAccount(request.getFromAccount());
		transaction.setToAccount(request.getToAccount());
		transaction.setAmount(request.getAmount());
		transaction.setTransactionDate(LocalDateTime.now());
		try {
			// Step 1: Debit sender
			accountClient.debitAmount(request.getFromAccount(), request.getAmount());

			try {
				accountClient.creditAmount(request.getToAccount(), request.getAmount());

				// Step 3: Mark transaction successful
				transaction.setStatus(TransactionStatus.SUCCESS);

			} catch (Exception creditEx) {
				// Step 4: Rollback debit
				accountClient.creditAmount(request.getFromAccount(), request.getAmount());
				transaction.setStatus(TransactionStatus.FAILED);
				sb.append("Credit Error:"+"\n ");
			}

		} catch (Exception debitEx) {
			// Debit itself failed, can't proceed
			transaction.setStatus(TransactionStatus.FAILED);
			sb.append("Debit Error:");
		}
		transaction.setFailureReason(sb.toString());
		transaction = transactionRepository.save(transaction);
		
		return TransactionMapper.toResponse(transaction);

	}

	public List<TransactionResponse> getTransactionByAccount(Long userId) {
		List<Transaction> transactions = transactionRepository.findByFromAccountUserId(userId);

		return transactions.stream().sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
				.map(TransactionMapper::toResponse).collect(Collectors.toList());
	}
}
