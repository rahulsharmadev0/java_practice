package com.rslock.common;

public final class Tests {

    public static void main(String[] args) {
        testCase1();
        testCase2();
        testCase3();
        testCase4();
        testCase5();
        testCase6();
    }

    // 1. Multiple source files
    static void testCase1() {
        System.out.println("Test Case 1: Multiple source files");
        String[] args = { "-s", "My Folder\\file.txt, My Folder\\file2.txt" };
        try {
            RsLockConfig parsed = RsLockConfig.fromArgs(args);
            System.out.println("  " + parsed);
            System.out.println("  PASS\n");
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage() + "\n");
        }
    }

    // 2. Single source file
    static void testCase2() {
        System.out.println("Test Case 2: Single source file");
        String[] args = { "-s", "My Folder\\file.txt" };
        try {
            RsLockConfig parsed = RsLockConfig.fromArgs(args);
            System.out.println("  " + parsed);
            System.out.println("  PASS\n");
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage() + "\n");
        }
    }

    // 3. Single file with destination
    static void testCase3() {
        System.out.println("Test Case 3: Single source file with destination");
        String[] args = { "-s", "My Folder\\file.txt", "-d", "SomePath\\" };
        try {
            RsLockConfig parsed = RsLockConfig.fromArgs(args);
            System.out.println("  " + parsed);
            System.out.println("  PASS\n");
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage() + "\n");
        }
    }

    // 4. All flags combined
    static void testCase4() {
        System.out.println("Test Case 4: All flags (source + destination + keystore)");
        String[] args = { "-s", "My Folder\\file.txt", "-d", "SomePath\\", "-k", "rskeystore.p12" };
        try {
            RsLockConfig parsed = RsLockConfig.fromArgs(args);
            System.out.println("  " + parsed);
            System.out.println("  PASS\n");
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage() + "\n");
        }
    }

    // 5. Multiple files with all flags
    static void testCase5() {
        System.out.println("Test Case 5: Multiple files with all flags");
        String[] args = { "-s", "file1.txt, file2.txt", "-d", "output/", "-k", "keystore.p12" };
        try {
            RsLockConfig parsed = RsLockConfig.fromArgs(args);
            System.out.println("  " + parsed);
            System.out.println("  PASS\n");
        } catch (Exception e) {
            System.out.println("  ERROR: " + e.getMessage() + "\n");
        }
    }

    // 6. Error case - flag without value
    static void testCase6() {
        System.out.println("Test Case 6: Error handling - value without flag");
        String[] args = { "invalid_value", "-s", "file.txt" };
        try {
            RsLockConfig parsed = RsLockConfig.fromArgs(args);
            System.out.println("  " + parsed);
            System.out.println("  PASS\n");
        } catch (Exception e) {
            System.out.println("  ERROR (expected): " + e.getMessage() + "\n");
        }
    }

}
