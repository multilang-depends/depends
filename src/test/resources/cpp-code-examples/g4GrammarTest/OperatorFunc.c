void operator()(OutputStream& os, size_t len) {
            c = '\x7f';
            f = 9223372036854775807LL;
}

TestData data[] = {
    { 100, 0 },
    { 100, 1 },
    { 100, 10 },
    { 100, 100 },
    { 100, 101 },
    { 100, 1000 },
    { 100, 1024 }
};

void init_unit_test_suite( char* []) 
{
  if (to_wchar) {
#if HAVE_WCRTOMB
    if (from_wchar) {
      cd->lfuncs.loop_convert = wchar_id_loop_convert;
      cd->lfuncs.loop_reset = wchar_id_loop_reset;
    } else
#endif
    {
      cd->lfuncs.loop_convert = wchar_to_loop_convert;
      cd->lfuncs.loop_reset = wchar_to_loop_reset;
    }
  } 
}

void ZLIB_INTERNAL inflate_fast(strm, start)
z_streamp strm;
unsigned start;         /* inflate()'s starting value for strm->avail_out */
{}


typedef void (*encode_fn)(Encoder &);

void runEncodeDecode(Encoder &e, Decoder &d, void (*encode_fn)(Encoder &)){}

void functionCall(){
                uint8_t *location = (*setter)(mapAddress, key);
}

typedef Resolver* (ResolverFactory::BuilderFunc) a;


typedef Resolver* (ResolverFactory::*BuilderFunc)(const NodePtr &writer, const NodePtr &reader, const Layout &offset);

void typedefInFunc(){
int i = 1;
if (a){}
        unsigned int i = (wc >> (4*count)) & 0x0f;

    if (n >= result) {
      int count;
      r[0] = '\\';
      r[1] = u;
      r += 2;
      for (count = result-3; count >= 0; count--) {
        *r++ = (i < 10 ? '0'+i : 'a'-10+i);
      }
      return result;
    }
}


