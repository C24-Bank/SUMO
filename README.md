# sumo

***Scanning Utility Module with OCR***

SUMO is a library to recognize text from images. 
The library ships with a default UI and an verifying process to counteract misreads from the OCR.

- Comes with an **user interface** out of the box
- **Easy implementation** through a single fragment
- Internal **verification mechanism** to guarantee the correct output 
- **Highly Customizable** for all your needs
- You decide how fast and how secure your scan should be
- Fits into any architecture

# Implementation

Look through the example app to see different approaches of scan instances.
A simple implementation could look like this:

Step 1:

Think about whether you want a verification mechanism and what data you want to verify.
If you have data to want to make sure to recognize correctly create a Regex which matches your data.

![regex](./regex.png)

