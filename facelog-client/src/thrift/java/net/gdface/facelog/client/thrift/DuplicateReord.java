package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

@ThriftStruct("DuplicateReord")
public final class DuplicateReord extends Exception
{
    private static final long serialVersionUID = 1L;

    public DuplicateReord() {
    }


}
