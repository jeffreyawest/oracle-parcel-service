load data
 infile 'zipcode.csv'
 into table postalgeolocation
 fields terminated by "," optionally enclosed by '"'
 ( postalcode, city, state, latitude, longitude)
