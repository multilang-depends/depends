# Supported Dependency Types and Examples

*Depends* could extract the following dependency types:
1. Import
1. Contain
1. Parameter
1. Call
1. Return
1. Throw
1. Implement
1. Extend
1. Create
1. Use
1. Cast
1. ImplLink
1. Annotation
1. Mixin

For most of relation types, the name is self-explained. 
*ImplLink* relation need more information. 

* ImplLink

## Supported Lanaguages
C/C++, Python

## Brief Description
*ImplLink* could be explains as *implementation link*, or *implicit link*.
In c/cpp file, if function *foo* calls function *bar*, we have relations of foo-[calls]-bar.

But, if we only analyse it based on invocation, it means *foo* have a call relation with bar's *prototype*.
During runtime, the function called by *foo* is determined by link time.

*ImplLink* indicates that there is a implementation link between function *foo* and all of implementations of function *bar*.

## Example:

    //File A
    void foo(){
        bar();
    }
    
    //File B
    void bar();
    
    //File C
    void bar(){
    
    }
    //File D
    void bar(){
    }

The relations of above code will be:

* foo - [Call] - bar (File B)
* foo - [ImplLink] - bar (File C)
* foo - [ImplLink] - bar (File D)

# Examples of relations

## Import
### Supported Lanaguages
All lanaguages

### Brief Description

*Import* is a relation between files. It indicates that *File A* includes or imports from *File B*.

###Examples

#### C/C++

    #include "LocalHeader.h"

#### Java
    package a;
    import b.*;
    
#### Python

    import imported_a

#### Ruby    
    require 'require_2'
    
## Contain
### Supported Lanaguages
All lanaguages

### Brief Description

*Contain* is a relation between code elements(entities). It indicates that *Element A* contains *Element B*.
For example, A class could contains a member, A function could contains a variable, etc. 

###Examples

#### C/C++
    class A {
       B *b;
       C c;    
    };

    void foo(){
        B b;
    }
#### Java
    class A {
       B *b;
       C c;    
    }

    void foo(){
        B b;
    }
    
#### Python
    class A:
        var_1 = B()
        self.var_2 = B()
        
        def foo():
          global global_var
          global_var = B()     

#### Ruby    
    class A
        b = B.new
        def foo
            C = C.new
        end
    end
        
#### Ruby    
    class A
        b = B.new
        def foo
            C = C.new
        end
    end

## Parameter
### Supported Lanaguages
All lanaguages

### Brief Description

*Parameter* is a relation of function/method and it's parameters.  

### Examples

#### C/C++
    void foo(B b);
#### Java
    void foo(B b){}
    
#### Python
    def foo(b):
      pass

#### Ruby    
    def foo(b)
    end
        
## Call
### Supported Lanaguages
All lanaguages

### Brief Description

*Call* is a relation of function/method invocation.  

### Examples

#### C/C++
    void foo(){
        bar();
        m.baz();
        p->baz();
    }
    
#### Java
    void foo(){
        bar();
        m.baz();
    }    
#### Python
    def foo(b):
      bar()
      m.baz()

#### Ruby    
    def foo()
      bar
      bar()
      m.baz
      m.baz()
    end             

## Return
### Supported Lanaguages
All lanaguages

### Brief Description

*Return* is a relation of function/method and it's return type(s).  

### Examples

#### C/C++
    B foo(){
        
    }
    
#### Java
    B foo(){
     
    }    
#### Python
    def foo():
      b = B()
      return b

#### Ruby    
    class Class
      def foo
        c = Class.new
        return c
      end
      
      def bar
        c = Class1.new
        c
      end
    end         

## Throw
### Supported Lanaguages
C++,Java,Ruby, Python

### Brief Description

*Throw* is similar as *Return*, it is a relation of function/method and it's throws type(s).  

### Examples

#### C++
    void foo(){
        E e;
        throw e;
    }
    
#### Java
    void foo(){
        throw new E();
    }    
#### Python
    def t1():
      raise Bar()

### Ruby    
    class Class
      def Class.test
        raise Class1.new
      end
    end          
    
## Implement
### Supported Lanaguages
C,C++, Java

### Brief Description

*Implement* is a relation between a function or class implementation, and it's prototype/interface.
 
### Examples

#### C/C++
    //A.h
       void foo();
       
    //A.c
        void foo(){
    
        }

We say that foo() of A.c implements foo of A.h    

#### Java
    class A implements I {
    }
       
## Extends
### Supported Lanaguages
C,C++, Java, Python, Ruby

### Brief Description

*Extends* means inherit of OO lanaguage

 
 ## Create
 ### Supported Lanaguages
 C,C++, Java, Python, Ruby
 
 ### Brief Description
 
 *Create* is a relation of the function and objects it created
 
## Cast
### Supported Lanaguages
  C,C++, Java
  
### Brief Description
  *Cast* is a relation of an expression and the casted types
  
## Use
### Supported Lanaguages
  All lanaguages
  
### Brief Description
  *Use* is a relation of an expression and the types/variables used by the expression.
   
## Annotation
### Supported Lanaguages
  Java/Python
  
### Brief Description
  The *Annotation* relation in Java, and *Decorator* relation of Python.
  
## MixIn
### Supported Lanaguages
  Ruby
  
### Brief Description
  Same as *Mix-in* of Ruby lanaguage.
  
    
  
 
 