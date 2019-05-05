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

    private int t1;

    @ThriftField(value=2, name="t1", requiredness=Requiredness.REQUIRED)
    public int getT1() { return t1; }

    @ThriftField
    public void setT1(final int t1) { this.t1 = t1; }

    private int t2;

    @ThriftField(value=3, name="t2", requiredness=Requiredness.REQUIRED)
    public int getT2() { return t2; }

    @ThriftField
    public void setT2(final int t2) { this.t2 = t2; }

    private int t3;

    @ThriftField(value=4, name="t3", requiredness=Requiredness.REQUIRED)
    public int getT3() { return t3; }

    @ThriftField
    public void setT3(final int t3) { this.t3 = t3; }

    private int t4;

    @ThriftField(value=5, name="t4", requiredness=Requiredness.REQUIRED)
    public int getT4() { return t4; }

    @ThriftField
    public void setT4(final int t4) { this.t4 = t4; }

    private TokenType type;

    @ThriftField(value=6, name="type", requiredness=Requiredness.OPTIONAL)
    public TokenType getType() { return type; }

    @ThriftField
    public void setType(final TokenType type) { this.type = type; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("id", id)
            .add("t1", t1)
            .add("t2", t2)
            .add("t3", t3)
            .add("t4", t4)
            .add("type", type)
            .toString();
    }
}
