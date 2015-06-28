# Reading from files in Java

There are several different ways to read input from a text file, including the use of `Scanners` along with their various "next" methods, such as *nextInt()*. (There's a good explanation of this at [https://docs.oracle.com/javase/tutorial/essential/io/scanning.html][1].) Here is an example of a somewhat old-fashioned approach that has the advantage of working from within an application or an applet. The "`StreamTokenizer`" parses the contents of the input file into "tokens", which are basically words--sequences of characters delineated by whitespace. (By the way, the method outlined here does not use `FileReaders`, a technique outlined in most introductory Java textbooks. The `Resource` technique is what allows programs to run correctly both from within Java apps and applets.)

In the code below, *foo* must be an instance of a user-defined class (i.e., a class that you have written, not a system-provided class.) If this code was in a public, non-static method, you could omit the "*foo*".

```
try 
    { 
    //create file input stream. Note that foo must a member of a user-defined class
    InputStream inS = foo.getClass().getResourceAsStream("input.txt");    
    if (inS == null) {
       System.out.println("File not found: input.txt"); 
       return;
    }
    // A Reader reads input as characters, rather than raw data.
    BufferedReader buffR = new BufferedReader(new InputStreamReader(inS));
    //The stream tokenizer parses the characters into words, integers, etc.
    StreamTokenizer in = new StreamTokenizer(buffR);
    in.nextToken(); // Get the first token. (A token can be a word, a number,    EOF, etc.)
    while (in.ttype != in.TT_EOF) // Keep going until exhausting tokens 
    { 
       if (in.ttype == in.TT_WORD) {
           if (in.sval.equals("JOB")) {
               ... 
           }
           ...
       }
       in.nextToken(); // Get the next token 
    } 
} // yrt 
catch (IOException ex){ 
    System.out.println(ex.getMessage()); 
} 
finally { 
    try { 
       if (inS != null) inS.close(); 
    } 
    catch (IOException ex) 
    { 
       System.err.println("Had trouble closing input.txt.");
    } 
}
```

[1]: https://docs.oracle.com/javase/tutorial/essential/io/scanning.html