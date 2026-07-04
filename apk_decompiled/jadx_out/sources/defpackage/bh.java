package defpackage;

import android.graphics.Rect;
import android.view.ScrollCaptureSession;
import java.util.function.Consumer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bh extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public Object k;
    public Object l;
    public /* synthetic */ Object m;
    public final /* synthetic */ Object n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ bh(Object obj, Object obj2, Object obj3, Object obj4, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = obj;
        this.l = obj2;
        this.m = obj3;
        this.n = obj4;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((bh) i((ij) obj2, (hk) obj)).k(x31Var);
            case 1:
                return ((bh) i((ij) obj2, (gw0) obj)).k(x31Var);
            case 2:
                return ((bh) i((ij) obj2, (hk) obj)).k(x31Var);
            case 3:
                return ((bh) i((ij) obj2, (cq0) obj)).k(x31Var);
            case 4:
                return ((bh) i((ij) obj2, (hk) obj)).k(x31Var);
            case 5:
                return ((bh) i((ij) obj2, (hk) obj)).k(x31Var);
            default:
                return ((bh) i((ij) obj2, (hk) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        Object obj2 = this.n;
        switch (i) {
            case 0:
                return new bh((dh) this.k, (ScrollCaptureSession) this.l, (Rect) this.m, (Consumer) obj2, ijVar, 0);
            case 1:
                bh bhVar = new bh((os) this.l, (ky0) this.m, (Float) obj2, ijVar, 1);
                bhVar.k = obj;
                return bhVar;
            case 2:
                return new bh((gy0) this.k, (os) this.l, (ky0) this.m, (Float) obj2, ijVar, 2);
            case 3:
                bh bhVar2 = new bh((zp) this.l, (cq0) this.m, (gl) obj2, ijVar, 3);
                bhVar2.k = obj;
                return bhVar2;
            case 4:
                bh bhVar3 = new bh((ym0) this.l, (lv) this.m, (x90) obj2, ijVar, 4);
                bhVar3.k = obj;
                return bhVar3;
            case 5:
                bh bhVar4 = new bh((p21) obj2, ijVar);
                bhVar4.m = obj;
                return bhVar4;
            default:
                return new bh((ep0) this.k, (to0) this.l, (j80) this.m, (z71) obj2, ijVar, 6);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x01d6, code lost:
    
        if (defpackage.k81.u(r1, r4, r22) == r5) goto L100;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e6  */
    /* JADX WARN: Type inference failed for: r4v11, types: [sz0, kv] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x00e6 -> B:33:0x00b0). Please report as a decompilation issue!!! */
    @Override // defpackage.s9
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object k(java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 702
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bh.k(java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ bh(Object obj, Object obj2, Object obj3, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.l = obj;
        this.m = obj2;
        this.n = obj3;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public bh(p21 p21Var, ij ijVar) {
        super(2, ijVar);
        this.i = 5;
        this.n = p21Var;
    }
}
