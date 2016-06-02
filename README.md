Bachelorreferaat Erik Steenman
==============================

This is the code repository for the research component of my bachelor's thesis (bachelorreferaat) for the University of Twente. The goal of the research is to see if formal methods can be applied in a way that applies more to [Agile development](https://en.wikipedia.org/wiki/Agile_software_development) methods rather than the more traditional [Waterfall model](https://en.wikipedia.org/wiki/Waterfall_model).

Project Basis
=============
The method that is taken to do this is based on a [project for the former subject Verification Engineering](https://github.com/lesteenman/Verification-Engineering), completed with the [FMT department](http://fmt.cs.utwente.nl/) of the University of Twente. The approach that was taken then was to have a strict division of 'actors', each of which have a set of states, actions they can perform, and guards.

Actions are actions performed by the actor, such as a cart moving forward or a traffic light changing its color. They will generally change the actor's internal state.

Guards are used to allow or prevent actions by other actors, again based on internal state. This means that one traffic light can prevent another traffic light from changing to green as long as it is green itself.

This strict division makes it so that each actor only has to know about its own state, and what external actions would be undesirable given its own state. By not having to consider how the actions of one actor affect another actor, one can apply the benefits of Formal Methods without having to define a complete model, and it becomes easier to add new actors or actions later in the development process.

Using this project
==================
This repository contains programs that can convert a simple language into both an MCRL2 model and into part of a Java program. Examples of this language can be found in the _example_ and _rollercoaster_ directories (\*.im files). The scripts _parser.py_ and _analyzer.py_ are used to check the intermediary model files. The scripts _mcrl2generator.py_ and _javagenerator.py_ can be used to convert these in MCRL2 and Java, respectively. Example usage of these scripts can be found in _im2mcrl2.py_ and _im2java.py_.
