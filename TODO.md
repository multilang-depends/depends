# General
- provide a general mapping file (ask Prof. Cai)

# Java
- block varaibles with same name should be resovled.  
- override functions (same type but different parameters)

- support new definition of relations (only annotation left)
- support Annotation const
- support lamba expression
- support java 8 method reference
- support type bound
- g4: line533 expression identifier/rawType under explicitGenericInvocation  nonWildcardTypeArguments (explicitGenericInvocationSuffix | THIS arguments)
- SUPER superSuffix do not supported yet.
- support import x.*;


# C/C++
- should parse all includes and remove cycling include path
- extract all names and perform a list for final check
- cannot parse types which is in standard lib like std::string
- cannot parse complex types like namespace::abc (only simple type could be solved"
- function type of KnR
- add check - should resolve all macros, variants, functions, class types, etc in header file; 
- extract comments of function delcarator
- find parameter related conditional statements
- extract set of local varaibles;
- use STL to check whether the template is handled completely
- a lot of unsolved symboles should be resolved
- invoke compilers to check errors


#Test
cpp lang  /usr/include/c++/7/@/usr/include/@/usr/lib/gcc/x86_64-linux-gnu/7/include/@@/usr/include/x86_64-linux-gnu/c++/7/@@/usr/include/linux/@@/usr/lib/gcc/x86_64-linux-gnu/7/include/@@/usr/include/x86_64-linux-gnu/@@/usr/include/x86_64-linux-gnu/@@/usr/include/  cpptest



