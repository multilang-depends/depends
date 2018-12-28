class Customer
end

class CustomerWithFieldAndMethod
   @@no_of_customers=0
   def hello
      puts "Hello Ruby!"
   end
end


def newObject
  cust1 = Customer. new
  cust2 = Customer. new
end

class CustomerWithNewParameters
   @@no_of_customers=0
   def initialize(id, name, addr)
      @cust_id=id
      @cust_name=name
      @cust_addr=addr
   end
   def display_details()
      puts "Customer id #@cust_id"
      puts "Customer name #@cust_name"
      puts "Customer address #@cust_addr"
   end
end

cust1=CustomerWithNewParameters.new("1", "John", "Wisdom Apartments, Ludhiya")


cust1.display_details()
