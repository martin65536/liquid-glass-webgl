package defpackage;

import java.util.Comparator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class b6 implements Comparator {
    public final /* synthetic */ int a;

    public /* synthetic */ b6(int i) {
        this.a = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                return o20.i(((rn0) obj2).a, ((rn0) obj).a);
            case 1:
                byte[] bArr = (byte[]) obj;
                byte[] bArr2 = (byte[]) obj2;
                if (bArr.length != bArr2.length) {
                    return bArr.length - bArr2.length;
                }
                for (int i = 0; i < bArr.length; i++) {
                    byte b = bArr[i];
                    byte b2 = bArr2[i];
                    if (b != b2) {
                        return b - b2;
                    }
                }
                return 0;
            case 2:
                return o20.i(((v20) obj).b, ((v20) obj2).b);
            case 3:
                y10 y10Var = (y10) obj;
                y10 y10Var2 = (y10) obj2;
                return (y10Var.f - y10Var.e) - (y10Var2.f - y10Var2.e);
            case 4:
                z40 z40Var = (z40) obj;
                z40 z40Var2 = (z40) obj2;
                float f = z40Var.I.p.H;
                float f2 = z40Var2.I.p.H;
                if (f == f2) {
                    return o20.i(z40Var.t(), z40Var2.t());
                }
                return Float.compare(f, f2);
            default:
                return o20.i(((i70) obj).a, ((i70) obj2).a);
        }
    }
}
