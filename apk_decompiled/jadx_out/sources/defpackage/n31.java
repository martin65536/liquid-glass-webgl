package defpackage;

import java.nio.ByteBuffer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n31 {
    public static final ThreadLocal d = new ThreadLocal();
    public final int a;
    public final e3 b;
    public volatile int c = 0;

    public n31(e3 e3Var, int i) {
        this.b = e3Var;
        this.a = i;
    }

    public final int a(int i) {
        tc0 b = b();
        int a = b.a(16);
        if (a != 0) {
            ByteBuffer byteBuffer = (ByteBuffer) b.h;
            int i2 = a + b.e;
            return byteBuffer.getInt((i * 4) + byteBuffer.getInt(i2) + i2 + 4);
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [dc0, java.lang.Object] */
    public final tc0 b() {
        ThreadLocal threadLocal = d;
        tc0 tc0Var = (tc0) threadLocal.get();
        tc0 tc0Var2 = tc0Var;
        if (tc0Var == null) {
            ?? dc0Var = new dc0();
            threadLocal.set(dc0Var);
            tc0Var2 = dc0Var;
        }
        uc0 uc0Var = (uc0) this.b.a;
        int a = uc0Var.a(6);
        if (a != 0) {
            int i = a + uc0Var.e;
            int i2 = (this.a * 4) + ((ByteBuffer) uc0Var.h).getInt(i) + i + 4;
            int i3 = ((ByteBuffer) uc0Var.h).getInt(i2) + i2;
            ByteBuffer byteBuffer = (ByteBuffer) uc0Var.h;
            tc0Var2.h = byteBuffer;
            if (byteBuffer != null) {
                tc0Var2.e = i3;
                int i4 = i3 - byteBuffer.getInt(i3);
                tc0Var2.f = i4;
                tc0Var2.g = ((ByteBuffer) tc0Var2.h).getShort(i4);
                return tc0Var2;
            }
            tc0Var2.e = 0;
            tc0Var2.f = 0;
            tc0Var2.g = 0;
        }
        return tc0Var2;
    }

    public final String toString() {
        int i;
        int i2;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", id:");
        tc0 b = b();
        int a = b.a(4);
        if (a != 0) {
            i = ((ByteBuffer) b.h).getInt(a + b.e);
        } else {
            i = 0;
        }
        sb.append(Integer.toHexString(i));
        sb.append(", codepoints:");
        tc0 b2 = b();
        int a2 = b2.a(16);
        if (a2 != 0) {
            int i3 = a2 + b2.e;
            i2 = ((ByteBuffer) b2.h).getInt(((ByteBuffer) b2.h).getInt(i3) + i3);
        } else {
            i2 = 0;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            sb.append(Integer.toHexString(a(i4)));
            sb.append(" ");
        }
        return sb.toString();
    }
}
