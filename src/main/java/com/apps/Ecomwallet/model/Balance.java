package com.apps.Ecomwallet.model;

import javax.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Balance")
@EntityListeners(AuditingEntityListener.class)
public class Balance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "balanceAmt", columnDefinition="Decimal(10,2)")
	private double balanceAmt;
	
	@OneToOne(mappedBy = "balance")
	private UserAccount useraccount;
	
	public UserAccount getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(UserAccount useraccount) {
		this.useraccount = useraccount;
	}

	public Balance() {}
	
	public Balance(double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public int getId() {
		return id;
	}

	public void setBalanceId(int id) {
		this.id = id;
	}

	public double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

}
