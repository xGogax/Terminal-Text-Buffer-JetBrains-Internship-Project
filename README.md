# Terminal Buffer

A simple **terminal buffer implementation in Java** that simulates the
behavior of a terminal emulator screen, including:

-   screen buffer
-   scrollback buffer
-   cursor movement
-   text insertion and overwriting
-   color and style attributes
-   resizing the terminal
-   wide character support

The project also includes **automated tests** and an **interactive CLI
menu** for manual testing.

------------------------------------------------------------------------

# Features

## Screen Buffer

Stores the currently visible terminal screen as a fixed-size grid of
cells.

Supports: 
* writing characters
* inserting characters
* retrieving characters
* retrieving line contents
* retrieving attributes

## Scrollback Buffer

Stores lines that scroll off the visible screen.

Allows:
* retrieving scrollback lines
* accessing characters and attributes in scrollback
* clearing scrollback

## Cursor

Tracks the current position where text is written.

Supports:
* moving to a specific position
* automatic movement during writing
* clamping when out-of-bounds

## Attributes

Each cell stores:

-   foreground color
-   background color
-   style flags

Supported styles include:

-   **BOLD**
-   **ITALIC**
-   **UNDERLINE**
-   **STRIKETHROUGH**

------------------------------------------------------------------------

# Terminal Operations

Implemented operations include:

-   write text
-   insert text
-   new line
-   insert empty line
-   fill a line with a character
-   clear screen
-   clear screen + scrollback
-   resize terminal
-   get character at position
-   get attributes at position

------------------------------------------------------------------------

# UML Diagram

The following diagram shows the main classes and their relationships.

* Inside of UMLdiagram package

------------------------------------------------------------------------

# Project Structure

    terminal/
     ├── TerminalBuffer
     ├── Cursor
     ├── Line
     ├── Cell
     ├── Attributes
     ├── Color
     └── StyleFlag

    exception/
     ├── ScreenOutOfBoundsException
     └── ScrollbackOutOfBoundsException

    test/
     ├── TestCell
     ├── TestCursor
     ├── TestLine
     ├── TestTerminalBuffer
     └── InteractiveMenu

------------------------------------------------------------------------

# Testing

The project contains **multiple test classes using Java `assert`
statements** that verify the correctness of the implementation.

Tests cover:

-   writing characters
-   inserting characters
-   cursor movement
-   scrollback behavior
-   resizing the terminal
-   wide character handling
-   edge cases and out-of-bounds access

Example:

    java -ea test.TestTerminalBuffer

The `-ea` flag enables Java assertions.

------------------------------------------------------------------------

# Interactive Menu

An interactive CLI tool is provided for manual testing of the terminal
buffer.

It allows the user to:

-   write text
-   insert text
-   move the cursor
-   resize the terminal
-   change colors and styles
-   inspect characters and attributes
-   view scrollback content

Run it with:

    java test.InteractiveMenu

------------------------------------------------------------------------

# Example Operations

Some supported interactive commands include:

    Write text
    Insert text
    Move cursor
    Fill line
    Resize terminal
    Change foreground/background color
    Toggle style flags
    View screen content
    View scrollback

This allows users to experiment with the terminal behavior in real time.

------------------------------------------------------------------------

# Design Goals

The main goals of this project were:

-   simulate core terminal buffer behavior
-   maintain a clear and modular architecture
-   avoid unnecessary dependencies
-   provide extensive test coverage
-   support both automated and interactive testing

------------------------------------------------------------------------

# Author

**Lazar Stanković**\
Faculty of Electrical Engineering\
University of Belgrade
