package com.apps.Ecomwallet.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apps.Ecomwallet.Constants;
import com.apps.Ecomwallet.model.Transaction;
import com.apps.Ecomwallet.model.UserAccount;
import com.apps.Ecomwallet.repository.AccountRepository;
import com.apps.Ecomwallet.repository.TransactionRepository;
import com.apps.Ecomwallet.service.AccountService;
import com.apps.Ecomwallet.service.AccountServiceImp;
import com.apps.Ecomwallet.validation.TransactionValidation;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	@Autowired
	AccountService acctService;
	
	@Autowired
	AccountServiceImp acctServiceImp;
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	TransactionRepository transactionRepo;
	
	@Autowired
	TransactionValidation transactionValidation;
	
	@PostMapping("/userAccount")
	public ResponseEntity<?> createAccount(@RequestBody UserAccount userAccount) {
		JSONObject resp = new JSONObject();
		try{
			UserAccount user = acctService.createAccount(userAccount);
			if(user !=null) {
				resp.put(Constants.JSON_SUCCESS, Constants.SUCCESS);
				resp.put(Constants.JSON_BALANCE, user.getBalance().getBalanceAmt());
			}else{
				resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
				resp.put(Constants.JSON_ERROR_MSG, Constants.ERROR_CREATE_ACCOUNT_FAIL);
			}
		}catch(Exception e){
			resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
			resp.put(Constants.JSON_ERROR_MSG, Constants.ERROR_CREATE_ACCOUNT_FAIL);
		}
		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);
	}

	@PostMapping("/checkBalance")
	public ResponseEntity<?> checkBalance(@RequestBody Map<String, ?>input) {			
		String email = input.get(Constants.JSON_EMAIL).toString();
		JSONObject resp = new JSONObject();
		
		if(email == null || email.isEmpty()) {
			resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
			resp.put(Constants.JSON_ERROR_MSG, Constants.ERROR_INVALID_ACCOUNT);
			return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);	
		}

		try{
			UserAccount acct = acctService.getAccount(email);
			if(acct !=null) {
				resp.put(Constants.JSON_SUCCESS, Constants.SUCCESS);
				resp.put(Constants.JSON_BALANCE, acct.getBalance().getBalanceAmt());
			}else {
				resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
				resp.put(Constants.JSON_ERROR_MSG, Constants.ERROR_INVALID_ACCOUNT);
			}
		}catch(Exception e){
			resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
			resp.put(Constants.JSON_ERROR_MSG, Constants.ERROR_INVALID_ACCOUNT);
		}
		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);	
	}
	
	@PostMapping("/transferCredits")
	public ResponseEntity<?> transferCredits(@RequestBody Map<String, ?> inputs) {
		JSONObject resp = new JSONObject();

		String fromEmail = inputs.get(Constants.JSON_EMAIL).toString();
		String toEmail = inputs.get(Constants.JSON_TRANSFEREE).toString();
		double credit = Double.parseDouble(inputs.get(Constants.JSON_AMOUNT).toString());

		try {			
			String result = acctServiceImp.transferCredit(fromEmail, toEmail, credit);
			if(Constants.JSON_SUCCESS.equals(result)) {
				resp.put(Constants.JSON_SUCCESS, Constants.SUCCESS);
			}else {
				resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
				resp.put(Constants.JSON_ERROR_MSG, result);
			}
		} catch (Exception e) {
			resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
		}

		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);
	}
	
	@PostMapping("/getTransactionRecords")
	public ResponseEntity<?> getTransactionRecords(@RequestBody Map<String, ?>inputs) {
		JSONObject resp = new JSONObject();
		String email = inputs.get(Constants.JSON_EMAIL).toString();
		
		UserAccount acct = accountRepo.findByEmail(email);		
		try {
			if(!transactionValidation.chkValidUser(acct)){
				resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
				resp.put(Constants.JSON_ERROR_MSG, Constants.ERROR_INVALID_ACCOUNT);
			}else {
				List<Transaction> transList = acctService.getTransHistory(acct.getEmail());
				resp.put(Constants.JSON_SUCCESS, Constants.SUCCESS);
				resp.put(Constants.JSON_TRANSACTIONS, transList);
			}
		}catch (Exception e) {
			resp.put(Constants.JSON_SUCCESS, Constants.FAIL);
		}
		
		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);
	}
}
