# README

**This project is still under construction.**

Dentry is a simple commmand-line tool intended to
help with data entry. I created something like this
long ago in the form of an extremely simple Python
script that I used while grading exams (I was a teacher),
the point of it being to save me the time of searching
a spreadsheet for the student's name by scanning over
it with my eyes. I have abstracted the idea to make
it as broadly useful as possible while maintaining the
simplicity of use.

The idea is very simple. First you load in what I call
the "anchor" data: for example, a list of the names of
the students in your class. These are just unique identifiers. 
The program enters a loop
where it prompts you for an input string to select which
anchor from the list you want to append data to. You 
only need to type enough of a substring to uniquely 
identify the anchor data (the student's name). Having
uniquely identified a data entity using the anchor string,
it prompts you for the information you want to attach
to that entity.
Since the data will be entered and exported in the form of
text files, the data is handled as Strings, at least for
now.

It will be possible to export the data, when the session is
complete, into a CSV file.

## Noteworthy Considerations

* There is no data type validation as of now, nor any specific plan to implement any;

## Complexity

I'm aware that in building this program I have used
very powerful tools that I probably will not need. I'm 
doing that mainly because my purpose in writing this
program is to practice using those tools: Hibernate, for
example.
