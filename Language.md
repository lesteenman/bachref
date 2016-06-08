# Language description
The IM has a number of repeating constructs. These are mainly _identifiers_, _blocks_, _conditions_ and _actions_.

Identifiers are generally used to refer to a certain object, such as an actor, action or struct item. They have to be unique, and can consist of alphanumeric characters and underscores.

Blocks are used to show which lines belong with which grammatical construction, actor, action, or condition. A block starts at an opening brace and ends with a matching closing brace. A block is generally prefixed with a fixed token or an identifier.

Conditions and actions will be described later in this paper.

Comments can also be added to a model description. Everything after a $\#$ will be ignored by the parser.

An IM is constructed from 2 main elements: Sorts and Actors. Both are declared in their respective top-level block, between the braces of a _sorts_ or _actors_ block respectively. While sorts are completely optional, there has to be at least one actor, so the _actors_ block is required.

##Sort

A sort is a custom type that can be defined. One example of a custom sort is the position of a switch (i.e. up or down). In this iteration of the grammar, only one type of custom sorts is supported: Structs. These consist of a list of possible items.

The sorts block is prefixed with the _sorts_ token. In the block, any number of sorts can be defined by a _struct()_ token, prefixed with the identifier of that sort. Within the struct's parentheses, a list of comma-separated identifiers identify which values that type can have.

```
sorts: {
  Segment: struct(station, lift, main, braking)
  SwitchPos: struct(up, down)
}
```

##Actor

Within the _actors_ block, we can have multiple _actor_s defined. Each _actor_ is denoted by an _identifier_, followed by a block that contains its properties.

```
actors: {
  cart: {
    ...
  }
  switch: {
    ...
  }
}
```

Each _actor_ has 4 types of properties: _instances_, _states_, _actions_ and _guards_. _States_ are optional, and if an _actor_ has no _states_, then its _instances_ are optional as well. Finally, both the _actions_ and _guards_ are optional, but an _actor_ with neither of those will not do anything useful.

```
states: {
  position: Segment
  locked: Bool
}
```

A basic _actor_ will have one _instance_. In this case, it suffices to simply have a list of _assignment_s for each of the _actor_'s states in the _instances_ block. In that case, the _identifier_ of the _actor_ will be its block's _identifier_. However, if you want multiple _actor_s with exactly the same behaviour, you can use the _instances_ block to do so. Simply add a block, prefixed by the _instance_'s _identifier_, for each of the _instances_ in this block. If you create more _instances_ this way, you can refer to either the _actor_ or a specific _instance_ in _actions_ and _guards_, by referring to the _actor_'s _identifier_ or the _instance_'s _identifier_ respectively.

```
# Single instance
instances: {
  position = main
  locked = true
}

# Multiple instances
instances: {
  cart1: {
    position = braking
    locked = false
  }
  cart2: {
    position = repair
    locked = false
  }
}
```

_Actor_s will often have one or more _states_ associated with them. These are defined in the _states_ block. Each _state_ is defined by an _identifier_, followed by a _type_. The _type_ can either be _Number_, _Boolean_ or a custom type as defined in the _sorts_ block.

##Actions and Guards

Each _actor_ can have any number of _actions_ and _guards_. Since the syntax for both is very similar, these will be treated together.

First, it is important to know the difference between _actions_ and _functions_, as the meaning of the terms may feel very similar.

An _action_ is any action an _actor_ can perform, such as moving or changing color. An _action_ has an _identifier_ and a list of _parameter_ _type_s it can take. Finally, an _action_ has one or more _function block_s, which contain _if/elseif/else_ statements and _functions_.

A _function_ is an execution of this _action_. It has a list of values for the _parameters_ of the _action_ which it can take, along with a list of _state_ changes that it causes.

An _action_ is defined by an _identifier_, which should describe what the _action_ does, and has one or more _function blocks_. Each _function block_ has a _function_ call, possibly being surrounded by _if/elseif/else_ statements.

A _function_ call describes what _parameters_ the _action_ can take, and what the resulting change in the _actor_'s _state_ is. It is written as:

```
-> <parameters> {
  <assignments>
}
```

The _parameters_ can be seen as a condition, as the _function_ can only be executed with those _parameters_.

A _parameter_ can either be a _value_ of the _type_ of the _parameter_, or it can be an _Any_ or _Not_ statement. These statements can be used for _struct_s. By giving _Any(SwitchPos)_ as a parameter to a function, the function can be called with either _up_ or _down_. 

A _Not_ statement has a similar function, but it also takes a list of either _struct_ _values_ or a _state_ of that _actor_ to be excluded. For example, by giving _Not(Segment: main, braking)_ as a _parameter_, the _function_ can be called with either _station_ or _lift_. If the _actor_ has a _state_ called _position_ of type _Segment_, you could also use that name in the list of excluded values.

Any function call can be surrounded by _if/elseif/else_ blocks. These behave in the same way they do in other languages. Only if the _conditions_ of the surrounding block are met, can the _function_ call (with the given _parameters_) be executed.

```
actions: {
  # define an action with 2 parameters
  # of type Segment
  forward(Segment, Segment): {
    # Allow this block if the actor's current
    # position is 'braking'
    if (position == lift) {
      # call the function
      -> lift, main {
        # change current position
        position = main
      }
    }
  }
}
```

For _actions_, this syntax is enough to define what an _actor_ can and can't do based on its own _states_. Basically the same syntax, however, can also be used to describe what another _actor_ can and can't do. In the _guards_ block, a block can be added for each _actor_ that you want to influence. Inside that block, one or more _function_s of the guarded _actor_ can be influenced.

```
# In the switch actor
guards: {
  # Influence any cart's actions
  cart: {
    # Influence the 'forward' action of any cart
    forward: {
      if (switch_position == down) {
        # Allow cart to move from braking to station
        # The state of the switch does not change
        -> braking, station { }
      } elseif (switch_position == up) {
        -> repair, station { }
      }
      # Regardless of the switch's own state, always
      # allow the cart to move anywhere else.
      -> Any(Segment), Not(Segment: station) { }
    }
  }
}
```
