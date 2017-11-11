package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

@ThriftStruct("DuplicateReordException")
public final class DuplicateReordException extends Exception
{
    private static final long serialVersionUID = 1L;

    public DuplicateReordException() {
    }


}
