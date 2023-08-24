# Probabilistic Inference Engine Implementation

## Description

## Example

Let's try to predict if today it will rain (typical dutch problem...).

### Facts

- SunShine : 0.25

There is just a little of sunshine

### Rules

- RULE_1 : Clouds = ! SunShine

The clouds are as much as not the sunshine (the clouds are the opposite of sunshine)

- RULE_2 : Rain = Clouds - 0.15

The chances of rain depend on how many clouds there are, just a little less.



### Execution

- Rule RULE_1 ? Clouds = ! SunShine => 
Clouds = 0.75

- Rule RULE_2 ? Rain = Clouds - 0.15 =>
Rain = 0.6

Result : **Rain = 0.6**

## Concepts

### Knowledge base
How we define facts?

Fact: a value between 0 and 1 linked to a label

The Knowledge base is loaded as a set of fact definitions,
where if a fact has a negative "value" it means it's not known.

Loading a Knowledge base from a file:

- JSON format
- Flat text file format

### Facts
A fact can have a value set a-priori or deduced
by the inferential engine.

A fact has 2 + 1 attributes:
- name : the unique id of the fact itself
- value : the probability of the "trueness" of the fact,
  between 0 and 1 (and negative if it is not defined)
- description : an useful textual description of the fact
  to facilitate the human reading of the reasoning

If a fact has a negative "value" it means
it's not known or not yet defined.

The engine will try to use the known facts and the rules
to compute the unknown ones.


### Rules
**How we write a ruleInterface?**

- input as a string
- parse the rule
- identify the asserted fact
- identify the formula

A rule has the logical format:

IF (x) THEN y

where x is a formula and y is a fact,
where the fact will be set to a certain
degree of "trueness" from 0 (absolute false)
to 1 (absolute true)

Since we are talking about probabilities,
we can always set the y fact
as the result of the formula

y = (x)

So the rule format is:

`<FACT> = <FORMULA>`

### Formula

Formulas should be written in a well-formed way,
with spaces between both symbols, operators and values, as for example

`( A + B - 0.1 ) > C`

Please note the spaces around the brackets.

All the numbers must be in a float format,
which means that 1 should be written 1.0 and even 0 should be 0.0 .


### Operators

Which operations do we support?

#### Boolean

#####  & (boolean and)
#####  | (boolean and)
#####  ! (boolean not)


#### Probabilistic

##### max
##### min
##### avg

### Inferring engine

#### How does the engine works?

#### When does it stop?

### Questions (execution)


## References:

https://blog.gitnux.com/code/java-ruleInterface-engine/

https://en.wikipedia.org/wiki/Forward_chaining

https://home.agh.edu.pl/~ligeza/wiki/lib/exe/fetch.php?media=ke:ruleinfalg.pdf



## Tools Used

### Command Line Interface

https://commons.apache.org/proper/commons-cli/index.html


