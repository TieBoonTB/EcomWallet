package com.apps.Ecomwallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.apps.Ecomwallet.model.Transaction;
import com.apps.Ecomwallet.model.UserAccount;
import com.apps.Ecomwallet.repository.AccountRepository;
import com.apps.Ecomwallet.repository.BalanceRepository;
import com.apps.Ecomwallet.repository.TransactionRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
class EcomwalletApplicationTests {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	TransactionRepository transactionRepo;

	@Autowired
	BalanceRepository balanceRepo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testFindByEmail() throws Exception {
		UserAccount testAcct = new UserAccount("test@test.com");
		testAcct.newAccount();
		entityManager.persist(testAcct);
		entityManager.flush();
		
		UserAccount result = accountRepo.findByEmail(testAcct.getEmail());
		assertEquals(result.getEmail(), testAcct.getEmail());
	}
	
	@Test
	public void testUpdateBalance() throws Exception {
		UserAccount testAcct = new UserAccount("test@test.com");
		testAcct.newAccount();
		entityManager.persist(testAcct);
		entityManager.flush();

		int result = balanceRepo.updateBalance(testAcct.getBalance().getId(), 2000.0);		
		
		assertEquals(result, 1);
	}

	@Test
	public void testRetrieveTransactionHistory() throws Exception {
		Transaction testTransData1 = new Transaction("test@test.com", "test1@test.com", Constants.TYPE_TRANSFER, 100.0);
		Transaction testTransData2 = new Transaction("test1@test.com", "test@test.com", Constants.TYPE_TRANSFER, 8.0);
		entityManager.persist(testTransData1);
		entityManager.persist(testTransData2);
		entityManager.flush();

		List<Transaction> result = transactionRepo.getTransHistory("test@test.com");
		assertEquals(result.size(), 2, 0);
	}
	
}
