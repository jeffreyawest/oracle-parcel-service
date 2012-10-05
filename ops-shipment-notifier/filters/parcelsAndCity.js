cityFilter = new LikeFilter("getToAddress.getCity", "Springfield")
parcelCount = new GreaterEqualsFilter("getParcels.size", new Integer(3))

//Assign the final filter back to 'filter'
filter = new AndFilter(cityFilter, parcelCount)
