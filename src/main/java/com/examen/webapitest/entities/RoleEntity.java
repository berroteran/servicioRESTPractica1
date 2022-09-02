package com.examen.webapitest.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 75180180824971012L;

		@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	    private Long id;

	    @Column(name="role_name")
	    private String roleName;

	    @Column(name="description")
	    private String description;

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}

		/**
		 * @return the roleName
		 */
		public String getRoleName() {
			return roleName;
		}

		/**
		 * @param roleName the roleName to set
		 */
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String getAuthority() {
			return this.roleName;
		}
	    
	    

	
}
