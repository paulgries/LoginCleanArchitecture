# Clean Architecture

# Preamble

This is an example of a Clean Architecture implementation.

We keep showing you the Clean Architecture picture, the green, pink, yellow, and
white picture with all the boxes and arrow. We will call this image the /"CA
Engine"/.

Pro tip: as you read this document, you'll want to look at the CA Engine a lot.
Pull it up on your phone if you don't have an external monitor. Or arrange your
laptop windows side by side. Or find the Engine in the textbook, if you have
a hard copy.

## The Java implementation

These packages correspond to the various areas of the program:

* `data_access`
* `entity`
* `interface_adapter`
* `use_case`
* `view`

There is one more package, `app`, that contains classes whose job it is to build
the CA Engine and start it running.

In most cases, each class is named according to its role in the CA Engine.

### Entities

These objects represent the fundamental data for the application. The classes
are named after concepts from the problem domain, like "bank account", "personal
profile", and "game board".

Most entities have a corresponding factory class. These classes manufacture
entities. See the Factory Pattern. The factories are mostly used in the data
access layer.

### Data Access

These Data Access Objects (DAO) store and retrieve entity data using files or a
database. They mostly do four things: _create_, _read_, _update_, and _delete_
(CRUD).

Often, there is one DAO object per entity, and that DAO reads and writes a
single file where each row contains information about one entity.

DAOs have a method that returns an entity object. There is often a map of keys
to entities, where the key is some kind of id.  When the main method creates a
DAO, it injects any necessary entity factories.

### Use Case Interactors

Use Case Interactor objects each do a single (possibly complicated) thing: given
data supplied by the user, do what the user wants with that data, then gather
any resulting data that the user wants to look at. That might be the result of a
bank transaction, updated data on your profile, or a move on a game board.

The data supplied by the user is gathered by the `Controller`, which tells the
`UseCaseInteractor` to do its job. The `Controller` passes in `InputData`, which
comes from the user through the `View`.

The state of the entities will often change as a result of a use case
interaction: some may be created, some may be deleted, and some may be mutated. 
`UseCaseInteractor`s use DAOs to get entities. When the main program creates an
interactor, it injects any necessary DAOs.

If the `UseCaseInteractor` needs to look up data -- perhaps fetch a bank account
balance -- then it will call a method in the appropriate DAO, which will look
up the required data and return an Entity containing it.

When the use case interaction is complete, the `UseCaseInteractor` will create
an `OutputData` object containing any new information that should be represented
in the `ViewModel`, and tells its `Presenter` object to update its `ViewModel`.

When the main program instantiates a `UseCaseInteractor`, it injects a
`Presenter`.

### View

Classes in the `view` package manage the user interface. `View` classes describe
different screens of the application. The `ViewManager`'s job is to swap which
screen is showing.

Most `View` objects have a corresponding `ViewModel` object in the
`interface_adaptor` package. Each `View` will listen to its `ViewModel`, and
react when it hears that there have been changes.

When the main program creates a `View`, it injects the `ViewModel`.

When the user performs an action, perhaps clicking a button, it causes a call on
an _action method_. When you create a button, you need to tell it to call an
`actionPerformed` method when it's clicked. Each button has its own
`actionPerformed` method. There are similar action methods for keystrokes and so on.

Each `actionPerformed` method calls a method in a `Controller` object to trigger
a use case interaction. When the main program builds the `View`, it injects any
necessary `Controller`s.

When an action method calls its `Controller` method, it passes in data the user
entered --- usually numbers and text, from text fields and so on.

### Interface Adapters

The `ViewModel` objects contain all data that is shown to the user. There is
usually one `ViewModel` per `View`, and one for the `ViewManager`. These view
managers know the objects that are listening to them, and tell them when the
data changes. (When we say "tell", we mean "call a method".)

There is typically one `Controller`, one `Presenter`, and one
`UseCaseInteractor` per action method.

`Controller` objects are given raw data from the `View`, and their job is to
make the data useful for a use case interaction. They might receive
`"26/04/2024"` as a `String` and instantiate a `LocalDateTime` object. Or they
might receive two integers, `42` and `55`, and create a `Currency` object
representing $42.55. ªOften, a `String` or number needs no such conversion, and
is left as-is.)

When the `Controller` has converted all the data, it put it into an Input Data
object that contains the information needed to execute the use case. The
`Controller` calls a method in the `UseCaseInteractor` to execute the use case
interaction.

`Controller`s and `Presenter`s never use entities.

When a `Controller` is instantiated, the main program injects a
`UseCaseInteractor` object.

A `Presenter`'s job is to update its `ViewModel`, which will tell the `View`
that there has been an update.

## A note about dependencies

The `UseCaseInteractor` is the heart of your program. Everything else exists to
support it. If you decide to switch from using plain-text files to using a
database, _the `UseCaseInteractor` should not change at all_.

To accomplish this, the `UseCaseInteractor` publishes a `DataAccessInterface`
specifying the operations it needs to save and retrieve data. The DAO class
implements this interface.

Remember that the main program injects the `DAO` into the `UseCaseInteractor`.
To change how you're persisting data, you would write a new `DAO` class that also
implements the `DataAccessInterface`, and then change the main program so that
it injects that `DAO` instead.

That's also why the `OutputBoundary` exists: if you want to change the user
interface (from, say, Java Swing to a web application), you would need to write
all new `View`s and perhaps new `ViewModel`s and `Controller`s and `Presenter`s.

The `InputBoundary` exists to make it clear how a `Controller` should use the
`UseCaseInteractor`.
