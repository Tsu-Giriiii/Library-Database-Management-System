
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class frontend {
    private JFrame frame;
    private JTextField titleField, authorField, yearField, copiesField, userIdField, bookIdField,newUserIdField, newUserNameField,newUseremailField,newUserNumberField;
    private JSpinner issueSpinner, returnSpinner;
    private JButton addButton, issueButton, returnButton,addUserButton,removeUserButton;
    private App app; // Instance of the App class for backend interaction

    public frontend() 
    {
        // Initialize the backend
        try {
            app = new App(); // This will connect to the database
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return; // If there's an issue with the database connection, stop
        }

        // Setup the JFrame and other components
        frame = new JFrame("Library Management");
        frame.setSize(1250, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Add background image
        JLabel background = new JLabel(new ImageIcon("src\\Library Management system background.jpg")); // Ensure the image path is correct
        background.setBounds(0, 0, 800, 500);
        frame.setContentPane(background);
        frame.setLayout(null); // Set layout after adding background

        // Add header
        JLabel headerLabel = new JLabel("Library Database Management System", JLabel.CENTER);
        headerLabel.setBounds(0, 10, 1200, 40);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setForeground(Color.WHITE);
        frame.add(headerLabel);

        // Add Book Section
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(50, 70, 80, 25);
        titleLabel.setForeground(Color.WHITE);
        frame.add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(150, 70, 200, 25);
        frame.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 110, 80, 25);
        authorLabel.setForeground(Color.WHITE);
        frame.add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(150, 110, 200, 25);
        frame.add(authorField);

        JLabel yearLabel = new JLabel("Published Year:");
        yearLabel.setBounds(50, 150, 100, 25);
        yearLabel.setForeground(Color.WHITE);
        frame.add(yearLabel);

        yearField = new JTextField();
        yearField.setBounds(150, 150, 200, 25);
        frame.add(yearField);

        JLabel copiesLabel = new JLabel("Stock:");
        copiesLabel.setBounds(50, 190, 80, 25);
        copiesLabel.setForeground(Color.WHITE);
        frame.add(copiesLabel);

        copiesField = new JTextField();
        copiesField.setBounds(150, 190, 200, 25);
        frame.add(copiesField);

        addButton = new JButton("Add Book");
        addButton.setBounds(150, 230, 100, 25);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String Year = yearField.getText();
                String copies = copiesField.getText();

                if (title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Title and Author cannot be empty.");
                } else {
                    String query = "INSERT INTO books (title, author,PublishedYear,CopiesAvailable) VALUES ('" + title + "', '" + author + "', '" + Year + "', '" + copies + "')";
                    try {
                        int rowsAffected = app.executeUpdate(query);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Book added successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to add the book.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error while adding the book: " + ex.getMessage());
                    }
                }
            }
        });
        frame.add(addButton);

        // Issue Book Section
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(500, 70, 80, 25);
        userIdLabel.setForeground(Color.WHITE);
        frame.add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(600, 70, 200, 25);
        frame.add(userIdField);

        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setBounds(500, 110, 80, 25);
        bookIdLabel.setForeground(Color.WHITE);
        frame.add(bookIdLabel);

        bookIdField = new JTextField();
        bookIdField.setBounds(600, 110, 200, 25);
        frame.add(bookIdField);

        JLabel issueDateLabel= new JLabel("Date of Issue:");
        issueDateLabel.setBounds(500,150,80,25);
        issueDateLabel.setForeground(Color.WHITE);
        frame.add(issueDateLabel);

        issueSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(issueSpinner, "yyyy-MM-dd");
        issueSpinner.setEditor(dateEditor);
        issueSpinner.setBounds(600,150,200,25);
        frame.add(issueSpinner);

        JLabel returnDateLabel= new JLabel("Date of Return:");
        returnDateLabel.setBounds(500,190,80,25);
        returnDateLabel.setForeground(Color.WHITE);
        frame.add(returnDateLabel);

        returnSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor rdateEditor = new JSpinner.DateEditor(returnSpinner, "yyyy-MM-dd");
        returnSpinner.setEditor(rdateEditor);
        returnSpinner.setBounds(600,190,200,25);
        frame.add(returnSpinner);
        

        issueButton = new JButton("Issue Book");
        issueButton.setBounds(600, 230, 120, 25);
        issueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                String bookId = bookIdField.getText();

                if (userId.isEmpty() || bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "User ID and Book ID cannot be empty.");
                } else {
                    String query ="INSERT INTO transactions (userID, bookID, IssueDate, DueDate) VALUES ('" 
                    + userId + "', '" + bookId + "', CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY))";
    
                    try {
                        int rowsAffected = app.executeUpdate(query);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Book issued successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to issue the book.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error while issuing the book: " + ex.getMessage());
                    }
                }
            }
        });
        frame.add(issueButton);

        // Return Book Section
        returnButton = new JButton("Return Book");
        returnButton.setBounds(600, 270, 120, 25);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText();
                String userID = userIdField.getText();

                if (bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Book ID cannot be empty.");
                } else {
                    String query = "DELETE FROM transactions WHERE BookID = '" + bookId + "'AND UserID = '"+userID+"' ";
                    try {
                        int rowsAffected = app.executeUpdate(query);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Book returned successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to return the book.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error while returning the book: " + ex.getMessage());
                    }
                }
            }
        });
        frame.add(returnButton);

        //user management section
        JLabel newUserIdLabel = new JLabel("User ID:");
        newUserIdLabel.setBounds(900, 70, 80, 25);
        newUserIdLabel.setForeground(Color.WHITE);
        frame.add(newUserIdLabel);

        newUserIdField = new JTextField();
        newUserIdField.setBounds(1000, 70, 150, 25);
        frame.add(newUserIdField);

        JLabel newUserNameLabel = new JLabel("User Name:");
        newUserNameLabel.setBounds(900, 110, 80, 25);
        newUserNameLabel.setForeground(Color.WHITE);
        frame.add(newUserNameLabel);

        newUserNameField = new JTextField();
        newUserNameField.setBounds(1000, 110, 150, 25);
        frame.add(newUserNameField);

        JLabel newUseremail = new JLabel("Email:");
        newUseremail.setBounds(900, 150, 80, 25);
        newUseremail.setForeground(Color.WHITE);
        frame.add(newUseremail);

        newUseremailField = new JTextField();
        newUseremailField.setBounds(1000, 150, 150, 25);
        frame.add(newUseremailField);

        JLabel newUserNumberLabel = new JLabel("Mobile No.:");
        newUserNumberLabel.setBounds(900, 190, 80, 25);
        newUserNumberLabel.setForeground(Color.WHITE);
        frame.add(newUserNumberLabel);

        newUserNumberField = new JTextField();
        newUserNumberField.setBounds(1000, 190, 150, 25);
        frame.add(newUserNumberField);

        addUserButton = new JButton("Add User");
        addUserButton.setBounds(1000, 230, 100, 25);
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = newUserIdField.getText();
                String userName = newUserNameField.getText();
                String email = newUseremailField.getText();
                String phone = newUserNumberField.getText();

                if (userId.isEmpty() || userName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "User ID and Name cannot be empty.");
                } else {
                    String query = "INSERT INTO users (userID, Name,Email,Phone) VALUES ('" + userId + "', '" + userName + "','"+email+"','"+phone+"')";
                    try {
                        int rowsAffected = app.executeUpdate(query);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "User added successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to add the user.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error while adding the user: " + ex.getMessage());
                    }
                }
            }
        });
        frame.add(addUserButton);

        removeUserButton = new JButton("Remove User");
        removeUserButton.setBounds(1100, 230, 120, 25);
        removeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = newUserIdField.getText();

                if (userId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "User ID cannot be empty.");
                } else {
                    String query = "DELETE FROM users WHERE userID = '" + userId + "'";
                    try {
                        int rowsAffected = app.executeUpdate(query);
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "User removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to remove the user.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error while removing the user: " + ex.getMessage());
                    }
                }
            }
        });
        frame.add(removeUserButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new frontend();
    }
}

