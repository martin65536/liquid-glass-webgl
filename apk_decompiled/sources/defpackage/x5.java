package defpackage;

import android.text.Layout;
import android.text.TextPaint;
import java.text.BreakIterator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x5 implements ak0 {
    public final String a;
    public final r11 b;
    public final List c;
    public final List d;
    public final wt e;
    public final mm f;
    public final j6 g;
    public final CharSequence h;
    public final p40 i;
    public r7 j;
    public final boolean k;
    public final int l;

    /* JADX WARN: Code restructure failed: missing block: B:162:0x04d2, code lost:
    
        if ((r4.b.c & 1095216660480L) == 0) goto L503;
     */
    /* JADX WARN: Code restructure failed: missing block: B:515:0x0097, code lost:
    
        if (r7 == 1) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x04b7  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x04dc  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x04f4  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0502  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x050c  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x05a4  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x065b  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0689  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x06d2  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x078c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x08d9  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x094b  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0975 A[LOOP:7: B:321:0x0973->B:322:0x0975, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:326:0x0986  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x09b2  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0704  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0647  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x0533  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x0541  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x0572  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x057b  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x057e  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x04e0  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:459:0x0304  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:462:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:463:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:472:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:475:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:486:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:490:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x00fd A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:493:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:498:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02ec A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00a7  */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Object, x5] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v82, types: [android.text.Spannable] */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.text.TextPaint, android.graphics.Paint, j6] */
    /* JADX WARN: Type inference failed for: r9v49, types: [java.lang.Object, a41] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public x5(java.lang.String r39, defpackage.r11 r40, java.util.List r41, java.util.List r42, defpackage.wt r43, defpackage.mm r44) {
        /*
            Method dump skipped, instructions count: 2515
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x5.<init>(java.lang.String, r11, java.util.List, java.util.List, wt, mm):void");
    }

    public final float a() {
        p40 p40Var = this.i;
        float f = p40Var.e;
        TextPaint textPaint = p40Var.b;
        if (!Float.isNaN(f)) {
            return p40Var.e;
        }
        BreakIterator lineInstance = BreakIterator.getLineInstance(textPaint.getTextLocale());
        CharSequence charSequence = p40Var.a;
        lineInstance.setText(new pd(charSequence, charSequence.length()));
        PriorityQueue priorityQueue = new PriorityQueue(10, k81.d);
        int i = 0;
        for (int next = lineInstance.next(); next != -1; next = lineInstance.next()) {
            if (priorityQueue.size() < 10) {
                priorityQueue.add(new w10(i, next, 1));
            } else {
                y10 y10Var = (y10) priorityQueue.peek();
                if (y10Var != null && y10Var.f - y10Var.e < next - i) {
                    priorityQueue.poll();
                    priorityQueue.add(new w10(i, next, 1));
                }
            }
            i = next;
        }
        float f2 = 0.0f;
        if (!priorityQueue.isEmpty()) {
            Iterator it = priorityQueue.iterator();
            if (it.hasNext()) {
                y10 y10Var2 = (y10) it.next();
                f2 = Layout.getDesiredWidth(p40Var.b(), y10Var2.e, y10Var2.f, textPaint);
                while (it.hasNext()) {
                    y10 y10Var3 = (y10) it.next();
                    f2 = Math.max(f2, Layout.getDesiredWidth(p40Var.b(), y10Var3.e, y10Var3.f, textPaint));
                }
            } else {
                v7.n();
                return 0.0f;
            }
        }
        p40Var.e = f2;
        return f2;
    }

    @Override // defpackage.ak0
    public final boolean b() {
        boolean z;
        r7 r7Var = this.j;
        if (r7Var != null) {
            z = r7Var.y();
        } else {
            z = false;
        }
        if (!z) {
            if (!this.k && dl.k(this.b)) {
                j2 j2Var = tq.a;
                j2 j2Var2 = tq.a;
                hy0 hy0Var = (hy0) j2Var2.f;
                if (hy0Var == null) {
                    if (oq.k != null) {
                        hy0Var = j2Var2.i();
                        j2Var2.f = hy0Var;
                    } else {
                        hy0Var = n20.d;
                    }
                }
                if (((Boolean) hy0Var.getValue()).booleanValue()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override // defpackage.ak0
    public final float g() {
        return this.i.c();
    }
}
