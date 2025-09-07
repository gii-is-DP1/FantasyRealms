package es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "username")
	@JsonIgnore
	User user;
	
	//@Enumerated(EnumType.STRING)
	@Column(length = 20)
	String authority;

	public Authorities() {}

	public Authorities(User user, String authority) {
		this.user = user;
		this.authority = authority;
	}
	
}
