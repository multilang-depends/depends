   void foo(){
           for (int i = 0; i < count; ++i, re *= im, im += 3) {
            ComplexInteger c(re, im);
            df.write(c);
        }
   }

    void foo(){
           for (int i = 0; i < count;) {
            ComplexInteger c(re, im);
            df.write(c);
        }
   }
   
