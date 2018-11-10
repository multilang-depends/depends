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

bool TypeInvStackSortCriterion::operator() (const void* a1, const void * a2) const
{
  const Interface* p1 = (const Interface*)a1;
  const Interface* p2 = (const Interface*)a2;

  //Note:  lower slot first
  //       high abstract layer first
  //       in same abstract, lower ifType first
  if (p1->slot()!=p2->slot())
  {
    return p1->slot()<p2->slot();
  }

  if (p1->getAbstractLayer()!=p2->getAbstractLayer())
  {
    // high layer first
    return p2->getAbstractLayer()<p1->getAbstractLayer();
  }

  if (p1->type() != p2->type())
  {
    return p1->type()<p2->type();
  }
  return p1->logical()< p2->logical();
}

