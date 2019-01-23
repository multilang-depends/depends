class Animal  
  def breathe  
  end  
end  

module Zoo
    class Animal  
      def breathe  
      end  
    end  
    
    class Cow < Animal
    end
end
  
class Cat < :Animal  
  def speak  
    puts "Miao~~"  
  end  
end  

class ZooCat < Zoo::Animal  
  def speak  
    puts "Miao~~"  
  end  
end  

