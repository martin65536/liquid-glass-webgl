package defpackage;

import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class l1 extends AccessibilityNodeProvider {
    public final c4 a;

    public l1(c4 c4Var) {
        this.a = c4Var;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
        k1 n = this.a.n(i);
        if (n == null) {
            return null;
        }
        return n.a;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final List findAccessibilityNodeInfosByText(String str, int i) {
        this.a.getClass();
        return null;
    }

    @Override // android.view.accessibility.AccessibilityNodeProvider
    public final AccessibilityNodeInfo findFocus(int i) {
        k1 n;
        c4 c4Var = this.a;
        h4 h4Var = (h4) c4Var.g;
        if (i != 1) {
            if (i == 2) {
                n = c4Var.n(h4Var.o);
            } else {
                throw new IllegalArgumentException("Unknown focus type: " + i);
            }
        } else {
            int i2 = h4Var.p;
            if (i2 == Integer.MIN_VALUE) {
                n = null;
            } else {
                n = c4Var.n(i2);
            }
        }
        if (n == null) {
            return null;
        }
        return n.a;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0053, code lost:
    
        if (r9 == false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:365:0x01bc, code lost:
    
        r1 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:520:0x0751, code lost:
    
        if (r0 != 16) goto L505;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:32:0x007f. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:320:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:526:0x0802  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0146  */
    /* JADX WARN: Type inference failed for: r7v22, types: [u0, s0] */
    /* JADX WARN: Type inference failed for: r7v28, types: [v0, s0] */
    @Override // android.view.accessibility.AccessibilityNodeProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean performAction(int r23, int r24, android.os.Bundle r25) {
        /*
            Method dump skipped, instructions count: 2300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l1.performAction(int, int, android.os.Bundle):boolean");
    }
}
