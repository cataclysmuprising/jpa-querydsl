INSERT INTO mjr_contact_info

(id,    contact_phone,       contact_email, 			     country, 			city, 				postal_code,	address, 			                                            record_reg_id ,record_upd_id ,record_reg_date ,record_upd_date )
VALUES
(1000,  '(01)099152591', 	'company1@example.com', 	    'Myanmar', 			'Yangon', 	 		'11171', 		'No.230, Cor of Anawrahta Rd &, Botataung Pagoda Rd' ,			0,0,current_timestamp,current_timestamp),
(1001,  '(02)4653063', 		'company2@example.com', 	    'Thailand', 		'Bangkok', 	 	 	'10400', 		'Room# M9 Pantip Plaza Building Petchburi Road' ,				0,0,current_timestamp,current_timestamp),
(1002,  '62783341', 		'company3@example.com', 	    'Singapore', 		'Singapore', 		'455286', 		'206 Upper East Coast Road #01-01 Capricorn Block' ,			0,0,current_timestamp,current_timestamp);

INSERT INTO mjr_customer

(id,    contact_info_id,    customer_name,       email, 			     password, 			                                                    balance, 				status,			record_reg_id ,record_upd_id ,record_reg_date ,record_upd_date )
VALUES
(1000,  1000, 	            'User 1', 	    	'user1@example.com', 	 '$2a$10$bQVFag1U8SkIdBcg7VVzSeoLoG9ddx/zl493lADgKGI4N7XofiPsi', 	 	700, 		            0,			    0,0,current_timestamp,current_timestamp),
(1001,  1001, 		        'User 2', 	        'user2@example.com', 	 '$2a$10$ctkkw.HhGMQx67TDD5qsTeZTqZnNCNwaHKUbREArUzdGUWxE6dWuS', 	 	800, 		            1,				0,0,current_timestamp,current_timestamp),
(1002,  1002, 		        'User 3', 	        'user3@example.com', 	 '$2a$10$XvhBG1Wnb42I4tmOZdNqg.0aTbpAArPLsnyQV.B2m3aXYImVqUkay', 		1500, 		            0,			    0,0,current_timestamp,current_timestamp);