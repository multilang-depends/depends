# Introduction

*Depends* is a source code dependency extraction tool, designed to infer syntactical relations among source code entities, such as files and methods, from various programming languages. Our objective is to provide a framework that is easily extensible to support dependency extraction from different programming languages and configuration files, so that other high-level analysis tools can be built upon it, in a language-independent fashion. Sample applications include code visualization, program comprehension, code smell detection, architecture analysis, design refactoring, etc.  

Our creation of *Depends* is motivated by the observations that different vendors, such as Understand&trade;, Structure 101 &trade;, and Lattix&trade;, created their own dependency extraction tools that are packaged with their other analysis functions. To conduct new analysis, vendors and researchers must each create their own dependency extraction tools, or use the output of other tools, which are usually not designed to be reusable. 

We believe that dependency analysis of software source code is one of the most important foundations of software engineering. Given the growing number of systems built on new languages and multi-languages, the need of a flexible, extensible multi-language dependency extraction framework, with simple and unified interfaces is widely recognized. 

*Depends* is open source and free, to promote community collaboration, to avoid repetitive work, and to improve the quality of analytical tools.

# How to use *Depends*

## Download and installation

You could download the latest version of *Depends* from https://github.com/multilang-depends/depends/releases/,  
and then unzip the ```depends-*version*.tgz``` file in any directory of your computer.

*Depends* is written in java, so it could be run on any OS with a JRE or JDK envoirment (like Windows, Linux or Mac OS). 

## Run it from commmand line

Following the single responsibility principle, *Depends* is designed for the purpose of extracting dependencies only. It only provides CLI interface, without GUI. But you can convert the output of *Depends* into the GUI of other tools, such as GraphViz(http://graphviz.org/), PlantUML(http://plantuml.com/), and DV8 (https://www.archdia.com). 

You could run *Depends* in the following ways: ```depends.sh``` on Linux/Mac, ```depends.bat``` on Microsoft Windows, or  ```java -jar depends.jar```.

Note: If you encountered Out of Memory error like ```GC overhead limt exceed```, please expands
the JVM memory like follwing ```java -Xmx4g -jar depends.jar <args>```.

## Parameters

The CLI tool usage could be listed by ```depends --help```, like following:

    Usage: depends [-hms] [--auto-include] [-d=<dir>] [-g=<granularity>]
                   [-p=<namePathPattern>] [-f=<format>[,<format>...]]...
                   [-i=<includes>[,<includes>...]]... <lang> <src> <output>
          <lang>                 The language of project files: [cpp, java, ruby, python,
                                   pom]
          <src>                  The directory to be analyzed
          <output>               The output file name
          --auto-include         auto include all paths under the source path (please
                                   notice the potential side effect)
      -i, --includes=<includes>[,<includes>...]
                                 The files of searching path
      -d, --dir=<dir>            The output directory
      -f, --format=<format>[,<format>...]
                                 The output format: [json(default),xml,excel,dot,
                                   plantuml]
      -g, --granularity=<granularity>
                                 Granularity of dependency.[file(default),method,L#(the level of folder. e.g. L1=1st level folder)  ]
      -h, --help                 Display this help and exit
      -s, --strip-leading-path   Strip the leading path.
      
      -m, --map                  Output DV8 dependency map file.
      -p, --namepattern=<namePathPattern>
                                 The name path separators.[default(/),dot(.)


To run *Depends*, you need to specify 3 most important parameters: ```lang```, ```src```,```output```, as explained above. 

## Remember to specify include paths

Please note that for most programming languages, such as ```C/C++, Ruby, Maven/Gradle```, the ```--includes``` path is important for *Depends* to find the right files when conducting code dependency analysis, similar to Makefile/IDE.  Otherwise, the extracted dependencies may not be accurate. 

Do not specify include paths outside of src directory (e.g. system level include paths, or external dependencies) because *Depends* will not process them.

```--auto-include``` is a useful parameter to simplify the input of include dirs: with this parameter, *Depends* will include all sub-directories of ```src```.

For ```Java``` programs, you are not required to specify include paths, because the mapping between java file paths are explicitly stated in the import statements.

### Output

The output of *Depends* can be exported into 5 formats: json, xml, excel, dot, and plantuml. Due to the limitation of MS excel,  you can only export into a excel file if the number of elements is less than 256.)

Dot files could be used to generate graphs using GraphViz (http://graphviz.org/).

Plantuml files could be used to generate UML diagram using PlantUML (http://plantuml.com/).

Json and XML files could be used to generate Design Structure Matrices (DSMs) using DV8 (https://www.archdia.net).

### How many dependency types does *Depends* support?

*Depends* supports major dependency types, including:
* Call: function/method invoke
* Cast: type cast
* Contain: variable/field definition
* Create: create an instance of a certain type
* Extend: parent-child relation
* Implement: implemented interface
* Import/Include: for example, java ```import```, c/c++ ```#include```, ruby ```require```.
* Mixin: mix-in relation, for example ruby include
* Parameter: as a parameter of a method
* Return: returned type
* Throw: throw exceptions
* Use: use or set variables

# How to contribute

There are many ways to contribute to *Depends*. For example:

## Support new languages

*Depends* implemented a graceful kernel for dependency analysis, which can be extended to accommodate various programming languages. So far, it only supports Java, C/C++, Ruby, and Maven. Please feel free to leverage this framework to add your own dependency extractor. 

The effort needed for each language varies a lot. We spent 24 hours to support Maven, but spent weeks to extract dependencies from Ruby

## Enhance language features and fix issues

Parsing source files is not trivial. There are many language-specific features that need to be taken into consideration. Reporting unsupported language features or fixing existing issues will make *Depends* better. 

## Create useful tools

You could use *Depends* as building blocks to create various tools, either open source or commercial, for productions or research, such as GUI tools, code visualization tools, etc.

## Become a sponsor

It will help us a great deal if your company or institute becomes a sponsor of our project. Your donation could help *Depends* to be independent, sustainable, and support more contributors.

# Tell us your thoughts

You are welcome to use "Depends" in your projects, either open source or commercial ones, as well as software engineering research. Your feedback is highly appreciated.  We will also be thankful if you could help us spread the words and encourage more people to use it.

# Acknowledgement

This project is built upon the excellent work of other researchers, including the ENRE framework (https://github.com/jinwuxia/ENRE) proposed by Jin Wuxia et al., and the architecture research from Prof. Yuanfang Cai 's team (https://www.cs.drexel.edu/~yfcai/) on dependency analysis.

The language specific front-end of *Depends* is built upon several excellent open source projects, including Antlr/Antlr Grammar V4 (https://github.com/antlr), Eclipse CDT (www.eclipse.org/cdt), and JRuby(https://github.com/jruby/jruby).

# Authors
 - Gang ZHANG (https://github.com/gangz)
 - Jin Wuxia (https://github.com/jinwuxia)

# Sponsors
 - ArchDia LLC. (www.archdia.com)
 - Emergent Design Inc.(www.emergentdesign.cn)

