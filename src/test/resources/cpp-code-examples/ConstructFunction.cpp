using namespace ItfMgnt;

BaseCmd::BaseCmd(BaseTrDe* td,char const* name,char const* des):
    des_m(des)
{
  td_m=td;
  assert_r(strlen(name)<=e_nameLen);
  strncpy(name_m,name,strlen(name)+1);
  td_m->add(this);
}
