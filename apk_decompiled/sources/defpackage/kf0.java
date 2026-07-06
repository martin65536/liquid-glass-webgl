package defpackage;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kf0 extends sz0 implements kv {
    public final /* synthetic */ int i = 1;
    public int j;
    public Object k;
    public Object l;
    public /* synthetic */ Object m;
    public Object n;
    public final /* synthetic */ Object o;
    public final /* synthetic */ Object p;
    public final /* synthetic */ Object q;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public kf0(ContentResolver contentResolver, Uri uri, a81 a81Var, zb zbVar, Context context, ij ijVar) {
        super(2, ijVar);
        this.m = contentResolver;
        this.n = uri;
        this.o = a81Var;
        this.p = zbVar;
        this.q = context;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((kf0) i((ij) obj2, (hk) obj)).k(x31Var);
            default:
                return ((kf0) i((ij) obj2, (ps) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.q;
        Object obj3 = this.p;
        Object obj4 = this.o;
        switch (i) {
            case 0:
                kf0 kf0Var = new kf0((gf0) obj3, (nf0) obj4, (sk) obj2, ijVar);
                kf0Var.m = obj;
                return kf0Var;
            default:
                kf0 kf0Var2 = new kf0((ContentResolver) this.m, (Uri) this.n, (a81) obj4, (zb) obj3, (Context) obj2, ijVar);
                kf0Var2.k = obj;
                return kf0Var2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0071 A[Catch: all -> 0x002e, TRY_LEAVE, TryCatch #3 {all -> 0x002e, blocks: (B:9:0x0028, B:11:0x0058, B:17:0x0069, B:19:0x0071, B:28:0x003e, B:30:0x0051), top: B:4:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0097  */
    /* JADX WARN: Type inference failed for: r0v12, types: [gv] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.Object, qf0] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0094 -> B:10:0x002b). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.kf0.k(java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public kf0(gf0 gf0Var, nf0 nf0Var, sk skVar, ij ijVar) {
        super(2, ijVar);
        this.p = gf0Var;
        this.o = nf0Var;
        this.q = skVar;
    }
}
