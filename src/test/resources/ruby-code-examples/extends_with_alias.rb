class Animal  
  def breathe  
  end  
end  

module Zoo
  class Animal
      def breathe  
      end      
  end
end  

alias Zoo_Animal Zoo::Animal

class Cat < Zoo_Animal  
  def speak  
    puts "Miao~~"  
  end  
end  
