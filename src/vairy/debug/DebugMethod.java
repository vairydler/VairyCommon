package vairy.debug;

public class DebugMethod {
	public enum EN_LogingType{
		ONLYTRUE,
		ONLYFALSE,
		ALL,
	}
	private final DebugWriter writer;
	private static EN_LogingType eloging;
	public DebugMethod(final DebugWriter writer) {
		this.writer = writer;
	}

	public static final EN_LogingType getEloging() {
		return eloging;
	}
	public static final void setEloging(EN_LogingType eloging) {
		DebugMethod.eloging = eloging;
	}
	public Boolean chkFalse(final Boolean bValue)
	{
		return chkTrue(!bValue);
	}
	public Boolean chkTrue(final Boolean bValue){
		Boolean bRet;

		if(bValue){
			bRet = true;
		}
		else{
			bRet = false;
		}

		if(null != writer)
		{
			StringBuilder builder = new StringBuilder(bRet + "\n");
			StackTraceElement[] stackTrace = new Exception().getStackTrace();
			Boolean bIsFirst = true;
			for(StackTraceElement elem : stackTrace){
				if(!bIsFirst){
					builder.append(elem.toString() + "\n");
				}else{
					bIsFirst = false;
				}
			}
			builder.append("-----------------------------------\n");

			Boolean bWriteLog = false;
			switch(eloging){
			case ALL:
				bWriteLog = true;
				break;
			case ONLYFALSE:
				if(!bRet){
					bWriteLog = true;
				}
				break;
			case ONLYTRUE:
				if(bRet){
					bWriteLog = true;
				}
				break;
			default:
				break;
			}

			if(bWriteLog){
				writer.writeFile(builder.toString());
			}
		}
		return bRet;
	}
}
