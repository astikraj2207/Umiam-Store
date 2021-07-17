<!-- Add banner here -->

# Project Title

An android application to order food items and other essentials from various stores inside our hostel. It uses Kotlin in android studio along with MySQL as database. PHP is used to access the local databse within the device.

This Project aims to save the precious time of students residing inside the hostel by providing facility to order food items and other essentials using this app and collecting them when they are ready rather than waiting in long queues

# Demo-Preview

Sign Up Page

![Demo](https://user-images.githubusercontent.com/58564174/126028783-a7ea1f62-cc4b-443a-9d22-0d4336643de4.gif)

Order Items

![Demo](https://user-images.githubusercontent.com/58564174/126028807-b195a858-af6b-4fea-a101-a3737e9941b7.gif)

View Items in Cart and Pay

![Demo](https://user-images.githubusercontent.com/58564174/126028851-6d7c7b61-449d-4e62-8bd5-574a014b28c1.gif)

Gpay Integrated

![Demo](https://user-images.githubusercontent.com/58564174/126028916-9ff17cc5-24d6-4d2d-a895-817ae35ea242.gif)

Admin Page

![Demo](https://user-images.githubusercontent.com/58564174/126028928-0636fe52-0173-480c-ac67-43ded5e44309.gif)





# Table of contents

- [Project Title](#project-title)
- [Demo-Preview](#demo-preview)
- [Table of contents](#table-of-contents)
- [Sign-Up](#sign-up)
- [Login](#login)
- [Landing-Page for Users](#landing-page-for-users)
- [Order Items](#order-items)
- [Verfiy Items in Cart and Pay](#verfiy-items-in-cart-and-pay)
- [UPI Integration](#upi-integration)
- [Contact-Us](#contact-us)
- [Admin-Landing Page](#admin-landing-page)


# Sign-Up

If a user is not registered, he can sign-up giving necessary information on the sign-up page. If a user with given email already exists, the app shows an error. Otherwise the user is logged in and a toast message is shown.

In the backend, the given information is used to open a url through which the corresponding data can be inserted into MySQL table app_users_table.


[(Back to top)](#table-of-contents)

# Login


The information entered is validated with data in app_users_table, if it is correct the user is directed to landing page. Otherwise an alert dialogue message is shown.

[(Back to top)](#table-of-contents)

# Landing-Page for Users


This Page shows the available shops for purchasing items i.e canteen, juice center and Stationary Shop.

The file fetch_brands.php fetches the required rows from products table in the database and displays it in the form of JSon objects array on a link. Android Stuido fetches the JSon object from the same link and displays it in the form of list view. 

[(Back to top)](#table-of-contents)

# Order Items

Whenever a user selects a particular shop, a new activity opens showing all products of the selected shop. The user can select any number of items and add it in cart. User can go back to the landing page for other ordering items from other shops. Also he can see the orders whose payment is yet to be done.

Products of the selected shop are fetched from database using fetch_products.php file and shown on the screen using recycler view. Using insert_temporary_product.php file all the un-confirmed orders of that shop are added to temporary_place_order table.

[(Back to top)](#table-of-contents)

# Verfiy Items in Cart and Pay

Items can de deleted from cart by simply clicking on the delete button. To order the selected items, click on the pay button at the top.

On clicking the delete button, decline_one_order.php runs and deletes the selected item from temporary_place_order table.

[(Back to top)](#table-of-contents)

# UPI Integration
The total price is shown on this page and order can be completed by entering user's UPI ID. If the payment is successful the corresponding orders will be shown to the Shop Owner.

The final price of all items in cart is calculated using calculate_total_price.php which utilises tables invoice and invoice_details. The calculated price is displayed on a link from which it is fetched by android studio.

[(Back to top)](#table-of-contents)

# Contact-Us
It displays the contact numbers of all shop owners to contact in case of any discrepancy.

[(Back to top)](#table-of-contents)

# Admin-Landing Page
If a user logs in as admin, he can see room numbers of all students who have ordered something in the form of a list. 
On Clicking any room number, orderes corresponding to that student are shown. The admin can clear the delivered items from this list once the order is delivered.

Here fetch_paid_items and delete_paid_items are the PHP files used for manipulating and interacting with database.

[(Back to top)](#table-of-contents)





