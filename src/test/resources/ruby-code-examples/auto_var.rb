def method1 (param1)
end

def method_with_local_var
    var_1 = 1
end 


def method_with_local_var_2times
    var_1 = 1
    var_1 = 1
end 

def method_without_local_var_and_param(param1)
    param1 = 1
end 

file_level_var = 0

def method_access_file_level_var(param1)
    file_level_var = 1
end 

module M
    module_level_var = 1;
    class C
        class_level_var = 1;
        def method(param1)
            file_level_var = 1
            module_level_var = 1;
            class_level_var = 1;
            local_var = 1;
        end 
        
        def method2
            local_var = 1;
        end
        
        def method_use_class_var
             $global_var = 1;
             @@class_var = 1;
             @instance_var = 1;
        end
    end
end

module Block
    block { a =1; }
end


