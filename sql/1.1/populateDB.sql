INSERT INTO pms.developers 
		(id,first_name,last_name) 
	VALUES 
		(1,'Mykhailo','Kosinskyi'), 
		(2,'Vladyslav','Len'''), 
		(3,'Yurii','Malikov'), 
		(4,'Mykhailo','Senchuk'), 
		(5,'Oleg','Volkov');

INSERT INTO pms.skills 
		(id,skill_name) 
	VALUES 
		(1,'Java'), 
		(2,'SQL'), 
		(3,'Spring'), 
		(4,'Junit'), 
		(5,'Maven');

INSERT INTO pms.companies 
		(id,company_name) 
	VALUES 
		(1,'Ciklum'), 
		(2,'EPAM'), 
		(3,'GlobalLogic'), 
		(4,'Luxoft'), 
		(5,'SoftServe');

INSERT INTO pms.customers 
		(id,customer_name)
	VALUES 
		(1,'City Bank'), 
		(2,'Rozetka'), 
		(3,'Ukrzaliznytsya');

INSERT INTO pms.projects 
		(id,company_id,customer_id,project_name) 
	VALUES 
		(1,1,1,'Financial Software'), 
		(2,2,3,'Ticketing Software'), 
		(3,5,2,'Website Project'), 
		(4,5,3,'CMS Software'), 
		(5,2,1,'Website Architecture');

INSERT INTO pms.developers_skills 
		(developer_id,skill_id) 
	VALUES 
		(1,1), 
		(1,2), 
		(1,5), 
		(2,1), 
		(2,3), 
		(3,1), 
		(3,4), 
		(4,1), 
		(4,3), 
		(4,5), 
		(5,1), 
		(5,2), 
		(5,3), 
		(5,4), 
		(5,5);

INSERT INTO pms.projects_developers 
		(project_id,developer_id) 
	VALUES 
		(1,1), 
		(1,3), 
		(1,5), 
		(2,2), 
		(2,5), 
		(3,4), 
		(3,5), 
		(4,1), 
		(4,2), 
		(4,5), 
		(5,1), 
		(5,2), 
		(5,3), 
		(5,4), 
		(5,5);