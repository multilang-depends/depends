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
- resolve macros
- parse extends relations
- should resolve operator overriding
- should parse all includes and remove cycling include path
- extract all names and perform a list for final check
- function type of KnR
- add check - should resolve all macros, variants, functions, class types, etc in header file; 
- find parameter related conditional statements
- use STL to check whether the template is handled completely
Macro should be unexpanded until last minutes, but some macros may neither vars nor functions, they could be certain informal format. **Which is difficult to handle **
 # Conditional Compliant Directives **Open** should we only handle the *visible* part, or everything of source code?
# Other
http://www.dependency-analyzer.org/




