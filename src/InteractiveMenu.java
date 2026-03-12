import terminal.Color;
import terminal.StyleFlag;
import terminal.TerminalBuffer;

import java.util.Scanner;

public class InteractiveMenu {
    public static Color chooseColor(Scanner sc, String type) {
        Color[] colors = Color.values();

        while (true) {
            System.out.println("\nChoose " + type + " color:");
            for (int i = 0; i < colors.length; i++) {
                System.out.println((i + 1) + " - " + colors[i]);
            }
            System.out.println("0 - Cancel");

            System.out.print("Enter option: ");
            String input = sc.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, enter a number.");
                continue;
            }

            if (choice == 0) {
                return null; // user canceled
            }

            if (choice >= 1 && choice <= colors.length) {
                return colors[choice - 1];
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    public static StyleFlag[] chooseStyleFlags(Scanner sc, StyleFlag[] currentStyles) {
        StyleFlag[] styles = StyleFlag.values();
        java.util.List<StyleFlag> selected = new java.util.ArrayList<>();
        if (currentStyles != null) {
            for (StyleFlag s : currentStyles) selected.add(s);
        }

        while (true) {
            System.out.println("\nChoose style flags (toggle selection):");
            for (int i = 0; i < styles.length; i++) {
                String mark = selected.contains(styles[i]) ? "[x]" : "[ ]";
                System.out.println((i + 1) + " - " + mark + " " + styles[i]);
            }
            System.out.println("0 - Done / Back");

            System.out.print("Enter option: ");
            String input = sc.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, enter a number.");
                continue;
            }

            if (choice == 0) {
                return selected.toArray(new StyleFlag[0]);
            }

            if (choice >= 1 && choice <= styles.length) {
                StyleFlag s = styles[choice - 1];
                if (selected.contains(s)) {
                    selected.remove(s); // toggle off
                } else {
                    selected.add(s);    // toggle on
                }
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Create terminal buffer");
        System.out.print("Enter width: ");
        int width = sc.nextInt();
        System.out.print("Enter height: ");
        int height = sc.nextInt();
        System.out.print("Enter scrollback size: ");
        int scrollback = sc.nextInt();
        TerminalBuffer buffer = new TerminalBuffer(width, height, scrollback);

        boolean running = true;

        while(running){
            System.out.println("\n========== TERMINAL MENU ==========");
            System.out.println("1 - Write Text"); // This will write text at the current cursor position, overwriting existing content and moving the cursor accordingly.
            System.out.println("2 - Insert Text"); // This will insert text at the current cursor position, shifting existing content to the right and moving the cursor accordingly.
            System.out.println("3 - New Line"); // This will move the cursor to the beginning of the next line. If the cursor is on the last line, it will scroll up and add a new empty line at the bottom.
            System.out.println("4 - Fill Line"); // This will fill the entire line at the specified row with a given character, overwriting existing content. The cursor position will not change.
            System.out.println("5 - Move cursor"); // This will move the cursor to the specified row and column. If the position is out of bounds, it will be clamped to the nearest valid position.
            System.out.println("6 - Resize"); // This will resize the terminal buffer to the new width and height. If the new size is smaller, content outside the new dimensions will be truncated. If larger, new empty space will be added. The cursor position will be adjusted if it is out of bounds after resizing.
            System.out.println("7 - Clear Screen"); // This will clear the screen by filling it with spaces and resetting all attributes to default. The scrollback will remain unchanged. The cursor will be moved to the top-left corner (0,0).
            System.out.println("8 - Clear Screen + Scrollback"); // This will clear both the screen and the scrollback buffer by filling them with spaces and resetting all attributes to default. The cursor will be moved to the top-left corner (0,0).
            System.out.println("9 - Show Screen"); // This will print the current visible screen content to the console, including all characters and their attributes. The scrollback content will not be shown.
            System.out.println("10 - Show Scrollback"); // This will print the entire scrollback buffer content to the console, including all characters and their attributes. The current screen content will not be shown.
            System.out.println("11 - Show Screen + Scrollback"); // This will print both the current visible screen content and the entire scrollback buffer content to the console, including all characters and their attributes. The screen content will be shown first, followed by the scrollback content.
            System.out.println("12 - Change Color/Style"); // This will allow you to change the current foreground color, background color, and style flags that will be applied to any new text written or inserted into the buffer. You can choose from the available colors and style flags defined in the Color and StyleFlag enums. The current attributes will not affect existing content on the screen or scrollback, only new content added after the change.
            System.out.println("13 - Reset Attributes"); // This will reset the current attributes (foreground color, background color, and style flags) to their default values. The default foreground color is white, the default background color is black, and the default style flags are none (no styles).
            System.out.println("14 - Get line at Position Screen"); // This will return the content of the line at the specified row on the visible screen, not including their style.
            System.out.println("15 - Get line at Position Scrollback"); // This will return the content of the line at the specified row in the scrollback buffer, not including their style.
            System.out.println("16 - Get attributes at Position Screen"); // This will return the attributes (foreground color, background color, and style flags) of the line at the specified row and column on the visible screen.
            System.out.println("17 - Get attributes at Position Scrollback"); // This will return the attributes (foreground color, background color, and style flags) of the line at the specified row and column in the scrollback buffer.
            System.out.println("18 - Get char at Position Screen"); // This will return the character at the specified row and column on the visible screen.
            System.out.println("19 - Get char at Position Scrollback"); // This will return the character at the specified row and column in the scrollback buffer.
            System.out.println("0 - Exit");
            System.out.println("==================================");

            System.out.print("Enter option: ");
            int option = sc.nextInt();
            sc.nextLine();

            try {
                switch (option) {
                    case 1:
                        System.out.print("Insert text to write: ");
                        String text = sc.nextLine();
                        buffer.write(text);
                        break;
                    case 2:
                        System.out.print("Insert text to insert: ");
                        String insertText = sc.nextLine();
                        buffer.insert(insertText);
                        break;
                    case 3:
                        buffer.insertEmptyLine();
                        break;
                    case 4:
                        System.out.print("Insert row to fill: ");
                        int row = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Insert character to fill with: ");
                        char c = sc.nextLine().charAt(0);

                        buffer.fillLine(row, c);
                        break;
                    case 5:
                        System.out.print("Insert row to move cursor to: ");
                        int moveRow = sc.nextInt();
                        System.out.print("Insert column to move cursor to: ");
                        int moveCol = sc.nextInt();
                        buffer.getCursor().setRow(moveRow);
                        buffer.getCursor().setCol(moveCol);
                        break;
                    case 6:
                        System.out.println("Insert new width: ");
                        int newWidth = sc.nextInt();
                        System.out.println("Insert new height: ");
                        int newHeight = sc.nextInt();
                        sc.nextLine();
                        buffer.resize(newWidth, newHeight);
                        break;
                    case 7:
                        buffer.clearScreen();
                        break;
                    case 8:
                        buffer.clearScreenAndScrollBack();
                        break;
                    case 9:
                        System.out.println(buffer.screenToString());
                        break;
                    case 10:
                        System.out.println(buffer.scrollbackToString());
                        break;
                    case 11:
                        System.out.println(buffer);
                        break;
                    case 12:
                        boolean attrMenu = true;

                        while(attrMenu) {
                            System.out.println("\n--- ATTRIBUTE MENU ---");
                            System.out.println("1 - Change BG color");
                            System.out.println("2 - Change FG color");
                            System.out.println("3 - Change StyleFlags");
                            System.out.println("0 - Back");

                            System.out.print("Choose option: ");
                            int attrOption = sc.nextInt();
                            sc.nextLine();

                            switch (attrOption){
                                case 1: // BG color
                                    Color bg = chooseColor(sc, "background");
                                    if (bg != null) {
                                        buffer.setCurrentBackground(bg);
                                        System.out.println("Background color set to " + bg);
                                    }
                                    break;
                                case 2: // FG color
                                    Color fg = chooseColor(sc, "foreground");
                                    if (fg != null) {
                                        buffer.setCurrentForeground(fg);
                                        System.out.println("Foreground color set to " + fg);
                                    }
                                    break;
                                case 3:
                                    StyleFlag[] newStyles = chooseStyleFlags(sc, buffer.getCurrentStyles());
                                    buffer.setCurrentStyles(newStyles);
                                    System.out.println("Current styles: " + java.util.Arrays.toString(newStyles));
                                    break;
                                case 0:
                                    attrMenu = false;
                                    break;
                                default:
                                    System.out.println("Invalid option. Please try again.");
                            }
                        }
                        break;
                    case 13:
                        buffer.setDefaultAttributes();
                        break;
                    case 14:
                        System.out.print("Row: ");
                        int rowScreen = sc.nextInt();
                        sc.nextLine();

                        System.out.println(buffer.getLineAtPositionScreen(rowScreen));
                        break;
                    case 15:
                        System.out.print("Row: ");
                        int rowScrollback = sc.nextInt();
                        sc.nextLine();

                        System.out.println(buffer.getLineAtPositionScrollback(rowScrollback));
                        break;
                    case 16:
                        System.out.print("Row: ");
                        int rowAttrScreen = sc.nextInt();
                        System.out.println("Column: ");
                        int colAttrScreen = sc.nextInt();
                        sc.nextLine();

                        System.out.println(buffer.getAttributesAtPositionScreen(rowAttrScreen, colAttrScreen));
                        break;
                    case 17:
                        System.out.print("Row: ");
                        int rowAttrScrollback = sc.nextInt();
                        System.out.println("Column: ");
                        int colAttrScrollback = sc.nextInt();
                        sc.nextLine();

                        System.out.println(buffer.getAttributesAtPositionScrollback(rowAttrScrollback, colAttrScrollback));
                        break;
                    case 18:
                        System.out.print("Row: ");
                        int rowCharScreen = sc.nextInt();
                        System.out.println("Column: ");
                        int colCharScreen = sc.nextInt();
                        sc.nextLine();

                        System.out.println(buffer.getCharAtPositionScreen(rowCharScreen, colCharScreen));
                        break;
                    case 19:
                        System.out.print("Row: ");
                        int rowCharScrollback = sc.nextInt();
                        System.out.println("Column: ");
                        int colCharScrollback = sc.nextInt();
                        sc.nextLine();

                        System.out.println(buffer.getCharAtPositionScrollback(rowCharScrollback, colCharScrollback));
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

    }
}