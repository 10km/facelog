package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;

@ThriftStruct("Token")
public final class Token
{
    public Token() {
    }

    private int id;

    @ThriftField(value=1, name="id", requiredness=Requiredness.REQUIRED)
    public int getId() { return id; }

    @ThriftField
    public void setId(final int id) { this.id = id; }

    private TokenType type;

    @ThriftField(value=2, name="type", requiredness=Requiredness.REQUIRED)
    public TokenType getType() { return type; }

    @ThriftField
    public void setType(final TokenType type) { this.type = type; }

    private long t1;

    @ThriftField(value=3, name="t1", requiredness=Requiredness.REQUIRED)
    public long getT1() { return t1; }

    @ThriftField
    public void setT1(final long t1) { this.t1 = t1; }

    private long t2;

    @ThriftField(value=4, name="t2", requiredness=Requiredness.REQUIRED)
    public long getT2() { return t2; }

    @ThriftField
    public void setT2(final long t2) { this.t2 = t2; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("id", id)
            .add("type", type)
            .add("t1", t1)
            .add("t2", t2)
            .toString();
    }
}
