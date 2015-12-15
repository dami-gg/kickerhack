# kickerhack

Zalando's hack week project for hacking a foosball table

## Table Tags

One of the main goals of this project is to attach every foosball table in the world with an NFC tag per player slot. Such a tag identifies the table and the player slot by carrying a *single* NDEF record using a *Mime Media Type* type name format.

		mime-type: zalando/application
		payload:
		{
			"table": 4, 
			"position": 0
		}