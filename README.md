# Introduction

*Depends* is a code dependency analysis tool. Its goal is to analyze the dependencies of software code, to better support software code visualization, program understanding,  bad smell detection, architecture refactoring, detection, and more.

*Depends* is open source and free. We believe that dependency analysis of software code is one of the infrastructures of software engineering. Adopting an open source approach can better promote community development, avoid repetitive labor, and improve the quality of analytical tools.

# How to use *Depends*

## Download and installation

You could download the latest version of depends via https://github.com/multilang-depends/depends/releases/ 
and then unzip the ```depends-*version*.tgz``` file in any directory of your computer.

*Depends* is written in java, so it could be run on any OS (like Windows, Linux or Mac OS) with a JRE or JDK envoirment. 


## Run it from commmand line

Follow the single responsibility principle, *Depends* is designed to be a minimum dependency analysis tool. It is only provide CLI interface, and without GUI support. (note: GUI tool will be created in seperated projects, and currently there is still no agenda on that).

You could run *Depends* by run ```depends.sh``` on Linux/Mac or ```depends.bat``` on Microsoft Windows.

## Parameters

The CLI tool usage could be listed by ```depends --help```, like following:

    Usage: depends [-hms] [--auto-include] [-d=<dir>] [-g=<granularity>]
                   [-p=<namePathPattern>] [-f=<format>[,<format>...]]...
                   [-i=<includes>[,<includes>...]]... <lang> <src> <output>
          <lang>                 The lanauge of project files: [cpp, java, ruby,
                                   pom]
          <src>                  The directory to be analyzed
          <output>               The output file name
          --auto-include         auto include all paths under the source path (please
                                   notice the potential side effect)
      -i, --includes=<includes>[,<includes>...]
                                 The files of searching path
      -d, --dir=<dir>            The output directory
      -f, --format=<format>[,<format>...]
                                 the output format: [json(default),xml,excel,dot,
                                   plantuml]
      -g, --granularity=<granularity>
                                 Granularity of dependency.[file(default),method,L#]
      -h, --help                 display this help and exit
      -s, --strip-leading-path   Strip the leading path.
      
      -m, --map                  Output DV8 dependency map file.
      -p, --namepattern=<namePathPattern>
                                 The name path pattern.[default(/),dot(.)


To run depends, the 3 most important parameters are: ```lang```, ```src```,```output```. The explaination is already given above.

## Rememeber to specify include paths

Please note that for most of lanaguages, like ```C/C++, Ruby, Maven/Gradle```, the ```--includes``` path is important for *Depends* to find the right file during code analysis, just like what you usually do in Makefile/IDE.  Otherwise, the dependency analysis could be inaccurate. 

You do not need to tell *Depends* the include paths outside of src directory (e.g. system level include path , or outside dependencies) due to *Depends* will not use them.

```--auto-include``` is a useful parameter to simplify the input of include dirs. It will include all sub-directories of ```src```.

For ```java``` language, you do not required to identify include paths, due to java built the mapping between java file path and import statements.

### Output

*Depends* support 5 output formats: json, xml, excel, dot, and plantuml. (Due to limitation of MS excel, the excel file is only outputted when the element size less than 256.)

Dot file could be used to generate graphics by GraphViz(http://graphviz.org/).

Plantuml file could be used to generate UML diagram by PlantUML(http://plantuml.com/).

# How to contribute

There are variety of ways to contribute to *depends*. For example:

## Supporting of new lanaguages

*Depends* implemented a graceful kernel for dependency analysis. However, it only supports very limited languages yet (so far they are Java, C/C++, Ruby and Maven). 

The effort required for each lanaguage is vary depends on different language. For example, Maven's support was completed in one night plus one day, while ruby's work lasted for weeks. 

## Enhance lanaguage features and fix issues

Reporting unsupported language features or fixing existing issues.

## Build useful tools upon *Depends*

To build useful tools, no matter open source tools or commercial tools, for example GUI tool, code visualization, etc.

## Become a sponsor

If your company or institute could become a sponsor of our project, which could keep *depends* project sustainable and focus on the most import technical things.

# Feedback to us

We will be very happy if *Depends* is applied to your project (including commercial projects) or software engineering research. Give us feedback on your usage is highly appreciated.

# Acknowledgement

The project built on a lot of existing previous works, especically the excellent work of Jin Wuxia on ENER(https://github.com/jinwuxia/ENRE) and the excellent work of Prof. Yuanfang Cai 's research team(https://www.cs.drexel.edu/~yfcai/) on dependency analysis.

*Depends* is built open the open source projects:

* Antlr and Antlr Grammar V4 (https://github.com/antlr)
* Eclipse CDT (www.eclipse.org/cdt)
* JRuby(https://github.com/jruby/jruby)

# Authors
 - Gang ZHANG (https://github.com/gangz)
 - Jin Wuxia (https://github.com/jinwuxia)
 
# Sponsors
 - ArchDia (www.archdia.net)
 - Emergent Design Inc.(www.emergentdesign.cn)
 
