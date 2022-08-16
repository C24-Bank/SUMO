# sumo

***Scanning Utility Module with OCR***

SUMO is a library to recognize text from images. 
The library ships with a default UI and an verifying process to counteract misreads from the OCR.

- Comes with an **user interface** out of the box
- **Easy implementation** through a single fragment
- Internal **verification mechanism** to guarantee the correct output 
- **Highly Customizable** for all your needs
- You decide how fast and how secure your scan should be
- Realize easy **UI feedback**
- Fits into any architecture

## Implementation

Look through the example app to see different approaches of scan instances.
A simple implementation could look like this:

### Step 1:

Think about whether you want a verification mechanism and what data you want to verify.
If you have data you need to make sure to recognize correctly create a Regex which matches your data.

![regex](./rm_regex.png)

Configure your scan with your desired options

![builder](./rm_builder.png)

if you need a custom UI use **Jetpack Compose** to create your own.

Make sure to place a FragmentContainerView if you are using a XML Layout.


As soon as you make a transaction and commit the ScanUIFragment you are all set to start scanning.


