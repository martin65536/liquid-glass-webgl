package defpackage;

import android.graphics.Canvas;
import android.graphics.RenderNode;
import android.widget.EdgeEffect;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class dx extends jm implements tp {
    public final /* synthetic */ int u = 1;
    public final e5 v;
    public final iq w;
    public Object x;

    public dx(yz0 yz0Var, e5 e5Var, iq iqVar, tj0 tj0Var) {
        this.v = e5Var;
        this.w = iqVar;
        this.x = tj0Var;
        D0(yz0Var);
    }

    public static boolean G0(float f, EdgeEffect edgeEffect, Canvas canvas) {
        if (f == 0.0f) {
            return edgeEffect.draw(canvas);
        }
        int save = canvas.save();
        canvas.rotate(f);
        boolean draw = edgeEffect.draw(canvas);
        canvas.restoreToCount(save);
        return draw;
    }

    public static boolean H0(float f, long j, EdgeEffect edgeEffect, Canvas canvas) {
        int save = canvas.save();
        canvas.rotate(f);
        canvas.translate(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
        boolean draw = edgeEffect.draw(canvas);
        canvas.restoreToCount(save);
        return draw;
    }

    public RenderNode I0() {
        RenderNode renderNode = (RenderNode) this.x;
        if (renderNode == null) {
            RenderNode f = kd0.f();
            this.x = f;
            return f;
        }
        return renderNode;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x02d9  */
    @Override // defpackage.tp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void R(defpackage.b50 r28) {
        /*
            Method dump skipped, instructions count: 1236
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dx.R(b50):void");
    }

    @Override // defpackage.tp
    public final /* synthetic */ void m0() {
        int i = this.u;
    }

    public dx(yz0 yz0Var, e5 e5Var, iq iqVar) {
        this.v = e5Var;
        this.w = iqVar;
        D0(yz0Var);
    }

    private final /* synthetic */ void J0() {
    }

    private final /* synthetic */ void K0() {
    }
}
