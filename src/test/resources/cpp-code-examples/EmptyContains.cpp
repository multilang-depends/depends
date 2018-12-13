void printCandidates(int cmd_m){

}
u_int32 BaseTrDe::executeCmd(char* Str,int* IdxP, char *Cmd)
{
  cmd_m=Cmd;
  parms_m=Str;
  idxP_m=IdxP;

  if (!strcmp(Cmd,"fullhelp"))
  {
    longHelp();
    return true;
  }

  BaseCmd *bc=find(cmd_m);
  char str[50];
  if (bc)
  {
    if (!dbg_scanstr(parms_m,IdxP,str))
    {
      if (!strcmp(str,"help"))
      {
        bc->help();
        return true;
      }
      (*idxP_m)--;
    }
    bc->handleCmd();
    return true;
  }
  else
    if (!handleCmd())
    {
      printCandidates(a(cmd_m));
      //dbg_m << "command not supported" << endl;
    }

  return false;
}