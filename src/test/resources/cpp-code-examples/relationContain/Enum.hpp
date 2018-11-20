#ifndef __ITFMGNT_PROFILER_HPP
#define __ITFMGNT_PROFILER_HPP
/******************************************************************************
**                                                                           **
**                             ALCATEL-LUCENT                                **
**                                                                           **
******************************************************************************/

/*************************** COPYRIGHT INFORMATION ****************************
**                                                                           **
** This program contains proprietary information which is a trade            **
** secret of ALCATEL-LUCENT and also is protected as an unpublished          **
** work under applicable Copyright laws. Recipient is to retain this         **
** program in confidence and is not permitted to use or make copies          **
** thereof other than as permitted in a written agreement with               **
** ALCATEL-LUCENT.                                                           **
**                                                                           **
******************************************************************************/

/****************************** IDENTIFICATION ********************************
**
** Project          : ISAM
**
** Module           : Equipment::ItfMgnt::ItfmProfiler
**
** File name        : ItfmProfiler.hpp
**
** Description      :
**
** References     : 3HH-05970-AGAA-DFZZA
**
** History
** 2010.01.29 huipingy ANTms87827: creation initial version
******************************************************************************/


#include <iocm/iocm.hh>
//////////////////////////////////////////////////////
//Profiler for performance

//#define ITFMTOOL_PERFORMANCE_MEASURE_ENABLED



typedef enum
{
	E_ItfId_getAbstractType,//0
	E_ItfId_getLogicalInfoEncapType,// 1
	E_ItfId_getMibType_with_logical,// 2
	E_ItfId_getMibType_with_type,// 3
	E_ItfId_hwReferenceTological,// 4
	E_ItfId_ifToLogical,// 5
	E_ItfId_indexInfoToIfIndex_getDynamic,// 6
	E_ItfId_indexInfoToIfIndex_with_abstractLevel,// 7
	E_ItfId_indexInfoToLogical,// 8
	E_ItfId_isDynamicVlanPort,// 9
	E_ItfId_logicalToHwReference,// 10
	E_ItfId_logicalToIf,// 11
	E_ItfId_logicalToIndexInfo,// 12
	E_StackMgnt_connectInterfaces,// 13
	E_ItfId_createInterface_withLower_withInfo,// 14
	E_ItfId_createInterface_withLower_noInfo,// 15
	E_ItfId_createInterface_noLower_withInfo,// 16
	E_StackMgnt_deleteInterface,// 17
	E_StackMgnt_disconnectInterfaces,// 18
	E_StackMgnt_getAbstractLayerInterfaces,// 19
	E_StackMgnt_getHigherInterfaces,// 20
	E_StackMgnt_getLogicalIndexesOfIfType,// 21
	E_StackMgnt_getLowerInterfaces,// 22
	E_StackMgnt_getNextIfdexPerMultiIfType,// 23
	E_StackMgnt_getNextIfIndex,// 24
	E_StackMgnt_getNextIfIndexPerType,// 25
	E_StackMgnt_isInterfaceExists,// 26
	E_StackMgnt_bulkReCreateInterfaces,// 27
	E_StackMgnt_ReCreateInterfaces_withLower_noInfo,// 28
	E_StackMgnt_ReCreateInterfaces_withLower_withInfo,// 29
	E_StackMgnt_ReCreateInterfaces_noLower_withInfo,// 30
	E_StackMgnt_registerEventHandler,// 31
	E_StackMgnt_registerInterfaceCreateDoneHandler,// 32
	E_StackMgnt_registerInterfaceRecreateHandler,// 33
	E_Linearisation_getLinearCapacity,// 34
	E_Linearisation_getMultiLinearCapacity,// 35
	E_Linearisation_logicalToLinear,// 36
	E_Linearisation_logicalToMultiLinear,// 37
	E_StackEvent_addLinkToGroup,// 38
	E_StackEvent_removeLinkFromGroup,// 39
	E_DataService_getAlarmSeverity,// 40
	E_DataService_setAlarmSeverity,// 41
	E_DataService_getCustomerId,// 42
	E_DataService_setCustomerId,// 43
	E_DataService_changeOperationalState,// 44
	E_DataService_getOperationalState,// 45
	E_DataService_getLastChange,// 46
	E_getBoardGroupForSlot,// 47
	E_logSlotToLinear,// 48
	E_DataService_changeOperationalStateBulk,// 49
	E_StackEvent_addLinkToLag,// 50
	E_StackEvent_removeLinkFromLag,// 51
	MAX_SERVICE_ID
}ServiceId;

class ServiceProfileInfo
{
public:
  ServiceProfileInfo();
  ~ServiceProfileInfo(){};
  void start();
  void stop();
  void clean();
  int count;
  int total_time;
private:
  unsigned long start_time;
};


class Profiler  
{
public:
	Profiler(ServiceId servId_i);
	virtual ~Profiler();
	void stop();
	static void print();
	static void clean();
private:
	static ServiceProfileInfo info[MAX_SERVICE_ID];
	ServiceId curId_m;
	bool      isStarted_m;
};


#ifdef ITFMTOOL_PERFORMANCE_MEASURE_ENABLED
    #define ITFMTOOL_PERFORMANCE_MEASURE(exp)   Profiler p(exp##);
#else
    #define ITFMTOOL_PERFORMANCE_MEASURE(ignore) ((void)0)
#endif


#endif //__ITFMGNT_PROFILER_HPP

